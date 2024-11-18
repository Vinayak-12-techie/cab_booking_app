package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.dto.DriverDto;
import com.vinayak.project.uber.uberApp.dto.RiderDto;
import com.vinayak.project.uber.uberApp.entities.Driver;
import com.vinayak.project.uber.uberApp.entities.Rating;
import com.vinayak.project.uber.uberApp.entities.Ride;
import com.vinayak.project.uber.uberApp.entities.Rider;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.vinayak.project.uber.uberApp.repositories.DriverRepo;
import com.vinayak.project.uber.uberApp.repositories.RatingRepo;
import com.vinayak.project.uber.uberApp.repositories.RiderRepo;
import com.vinayak.project.uber.uberApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepo ratingRepo;
    private final DriverRepo driverRepo;
    private final RiderRepo riderRepo;
    private final ModelMapper modelMapper;

    @Override
    public DriverDto rateDriver(Ride ride , Integer rating) {
        Driver driver=ride.getDriver();
        Rating ratingObj= ratingRepo.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("No Rating object found for the Ride "+ ride));

        if(ratingObj.getDriverRating()!=null)
            throw new RuntimeConflictException("Driver has already been rated ,cannot rate again");

        ratingObj.setDriverRating(rating);
        ratingRepo.save(ratingObj);

        Double newRating = ratingRepo.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average()
                .orElse(0.0);

        driver.setRating(newRating);

        Driver savedDriver =driverRepo.save(driver);
        return modelMapper.map(savedDriver,DriverDto.class);
    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {
        Rider rider=ride.getRider();
        Rating ratingObj= ratingRepo.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("No Rating object found for the Ride "+ ride));

        if(ratingObj.getRiderRating()!=null)
            throw new RuntimeConflictException("Rider has already been rated ,cannot rate again");

        ratingObj.setRiderRating(rating);
        ratingRepo.save(ratingObj);

        Double newRating = ratingRepo.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average()
                .orElse(0.0);

        rider.setRating(newRating);

        Rider savedRider= riderRepo.save(rider);
        return modelMapper.map(savedRider, RiderDto.class);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating= Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();

        ratingRepo.save(rating);
    }
}
