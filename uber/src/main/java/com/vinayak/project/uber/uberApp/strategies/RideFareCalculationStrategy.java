package com.vinayak.project.uber.uberApp.strategies;

import com.vinayak.project.uber.uberApp.dto.RideRequestDto;
import com.vinayak.project.uber.uberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER= 10;
    double calculateFare(RideRequest rideRequest);
}
