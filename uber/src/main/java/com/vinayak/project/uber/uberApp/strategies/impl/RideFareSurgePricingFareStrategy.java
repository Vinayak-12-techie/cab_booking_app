package com.vinayak.project.uber.uberApp.strategies.impl;

import com.vinayak.project.uber.uberApp.dto.RideRequestDto;
import com.vinayak.project.uber.uberApp.entities.RideRequest;
import com.vinayak.project.uber.uberApp.services.DistanceService;
import com.vinayak.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final Double SURGE_FACTOR = 2.0;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        // Due to @Getter & @Setter annotations we can get directly get the values of members without defining the actual getters & setter sin the class.
        double distance =distanceService.calculateDistance
                (rideRequest.getPickUpLocation(),rideRequest.getDropOffLocation());

        return distance*SURGE_FACTOR;
    }
}
