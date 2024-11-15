package com.vinayak.project.uber.uberApp.strategies;

import com.vinayak.project.uber.uberApp.strategies.impl.DriverMatchingHighestRated;
import com.vinayak.project.uber.uberApp.strategies.impl.DriverMatchingNearestDriver;
import com.vinayak.project.uber.uberApp.strategies.impl.RideFareCalculationDefault;
import com.vinayak.project.uber.uberApp.strategies.impl.RideFareSurgePricingFareStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRated driverMatchingHighestRated;
    private final DriverMatchingNearestDriver driverMatchingNearestDriver;
    private final RideFareSurgePricingFareStrategy rideFareSurgePricingFareStrategy;
    private final RideFareCalculationDefault rideFareCalculationDefault;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){
        if(riderRating>=4.8) return driverMatchingHighestRated;
        else return driverMatchingNearestDriver;
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){
        LocalTime surgeStartTime=LocalTime.of(18,0);
        LocalTime surgeEndTime=LocalTime.of(21,0);
        LocalTime currentTime=LocalTime.now();

        boolean isSurge = currentTime.isAfter(surgeStartTime)  && currentTime.isBefore(surgeEndTime);

        if(isSurge) return rideFareSurgePricingFareStrategy;
        else return rideFareCalculationDefault;
    }
}
