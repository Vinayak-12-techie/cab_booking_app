package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.dto.DriverDto;
import com.vinayak.project.uber.uberApp.dto.RideDto;
import com.vinayak.project.uber.uberApp.dto.RideRequestDto;
import com.vinayak.project.uber.uberApp.dto.RiderDto;
import com.vinayak.project.uber.uberApp.entities.*;
import com.vinayak.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.vinayak.project.uber.uberApp.entities.enums.RideStatus;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.repositories.DriverRepo;
import com.vinayak.project.uber.uberApp.repositories.RideRequestRepo;
import com.vinayak.project.uber.uberApp.repositories.RiderRepo;
import com.vinayak.project.uber.uberApp.services.DriverService;
import com.vinayak.project.uber.uberApp.services.RideService;
import com.vinayak.project.uber.uberApp.services.RiderService;
import com.vinayak.project.uber.uberApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepo rideRequestRepo;
    private final RiderRepo riderRepo;
    private final RideService rideService;
    private final DriverService driverService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider= getCurrentRider();
        // Creating and saving the RideRequest Object according to the Rider Request
        RideRequest rideRequest = modelMapper.map(rideRequestDto,RideRequest.class);
//        log.info(rideRequest.toString());
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare= rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);
        RideRequest savedRideRequest=rideRequestRepo.save(rideRequest);

        // Finding Matching Driver
        List<Driver> drivers=
                rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDrivers(rideRequest);

        //TODO: Send Notification to all the drivers about this request

        return modelMapper.map(savedRideRequest,RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider= getCurrentRider();
        Ride ride= rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider does not own this ride with id : "+rideId);
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled, invalid status "+ride.getRideStatus());
        }

        Ride savedRide= rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(),true);

        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider= getCurrentRider();
        return modelMapper.map(currentRider,RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider=getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider,pageRequest).map(
                ride->modelMapper.map(ride,RideDto.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider=Rider.builder().user(user).rating(0.0).build();
        return riderRepo.save(rider);

    }

    @Override
    public Rider getCurrentRider() {
        return riderRepo.findById(1L).orElseThrow(()-> new ResourceNotFoundException(
                "Rider not found with id 1"
        ));
    }
}
