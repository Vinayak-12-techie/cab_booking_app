package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.dto.DriverDto;
import com.vinayak.project.uber.uberApp.dto.RideDto;
import com.vinayak.project.uber.uberApp.dto.RiderDto;
import com.vinayak.project.uber.uberApp.entities.Driver;
import com.vinayak.project.uber.uberApp.entities.Ride;
import com.vinayak.project.uber.uberApp.entities.RideRequest;
import com.vinayak.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.vinayak.project.uber.uberApp.entities.enums.RideStatus;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.repositories.DriverRepo;
import com.vinayak.project.uber.uberApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepo driverRepo;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest=rideRequestService.findRequestById(rideRequestId);

        // We avoid nested if-else statements in prod code, so we try to return/throw error from the if block mostly.
        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride Request can not be accepted, status is : "+rideRequest.getRideRequestStatus());
        }

        Driver currentDriver= getCurrentDriver();
        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Driver cannot accept the Ride due to unavailability");
        }

        Driver savedDriver = updateDriverAvailability(currentDriver,false);
        rideService.createNewRide(rideRequest,currentDriver);
        Ride ride=rideService.createNewRide(rideRequest,savedDriver);

        return modelMapper.map(ride,RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride= rideService.getRideById(rideId);

        Driver driver= getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot cancel the ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled, invalid status "+ride.getRideStatus());
        }

        rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        updateDriverAvailability(driver,true);

        return modelMapper.map(ride,RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId,String otp) {
        Ride ride= rideService.getRideById(rideId);
        Driver driver= getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start the ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride Status is not not confirmed");
        }

        if(otp.equals(ride.getOtp())){
            throw new RuntimeException("Otp is not Valid");
        }

        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide= rideService.updateRideStatus(ride,RideStatus.ONGOING);

        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);

        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {
        Ride ride= rideService.getRideById(rideId);
        Driver driver= getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start the ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride Status is not not Ongoing, hence can't be ended");
        }

        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide=rideService.updateRideStatus(ride,RideStatus.ENDED);
        updateDriverAvailability(driver,true);

        paymentService.processPayment(ride);

        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride= rideService.getRideById(rideId);
        Driver driver= getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("You cannot rate the Rider");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("You cannot rate the rider until the ride is ended");
        }

        return ratingService.rateRider(ride, rating);
    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver=getCurrentDriver();
        return modelMapper.map(currentDriver,DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver=getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver,pageRequest).map(
                ride->modelMapper.map(ride,RideDto.class)
        );
    }

    @Override
    public Driver getCurrentDriver() {
        return driverRepo.findById(2L)
                .orElseThrow(()->new ResourceNotFoundException("Driver not found with the given id"));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, Boolean availability) {
        driver.setAvailable(availability);
        return driverRepo.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepo.save(driver);
    }
}
