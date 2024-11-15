package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.dto.RideRequestDto;
import com.vinayak.project.uber.uberApp.entities.Driver;
import com.vinayak.project.uber.uberApp.entities.Ride;
import com.vinayak.project.uber.uberApp.entities.RideRequest;
import com.vinayak.project.uber.uberApp.entities.Rider;
import com.vinayak.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.vinayak.project.uber.uberApp.entities.enums.RideStatus;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.repositories.RideRepo;
import com.vinayak.project.uber.uberApp.services.RideRequestService;
import com.vinayak.project.uber.uberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepo rideRepo;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepo.findById(rideId)
                .orElseThrow(()->new ResourceNotFoundException("Ride not found with the given id"));
    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        // As RideRequest & Ride entities have some common field , so we are mapping those fields first and then set all the other fields by ourselves.
        Ride ride=modelMapper.map(rideRequest,Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateOtp());
        ride.setId(null);

        rideRequestService.update(rideRequest);
        return rideRepo.save(ride);

    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepo.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepo.findByRider(rider,pageRequest);
    }


    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return rideRepo.findByDriver(driver,pageRequest);
    }

    private String generateOtp(){
        Random random=new Random();
        int otpInt=random.nextInt(10000); //0 to 9999
        return String.format("%04d",otpInt);
    }
}
