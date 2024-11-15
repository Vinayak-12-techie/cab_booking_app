package com.vinayak.project.uber.uberApp.strategies.impl;

import com.vinayak.project.uber.uberApp.dto.RideRequestDto;
import com.vinayak.project.uber.uberApp.entities.Driver;
import com.vinayak.project.uber.uberApp.entities.RideRequest;
import com.vinayak.project.uber.uberApp.repositories.DriverRepo;
import com.vinayak.project.uber.uberApp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DriverMatchingNearestDriver implements DriverMatchingStrategy {

    private final DriverRepo driverRepo;
    @Override
    public List<Driver> findMatchingDrivers(RideRequest rideRequest) {
        return driverRepo.findTenNearestDrivers(rideRequest.getPickUpLocation());
    }
}
