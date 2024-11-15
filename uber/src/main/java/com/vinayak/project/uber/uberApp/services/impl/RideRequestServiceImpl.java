package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.entities.RideRequest;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.repositories.RideRequestRepo;
import com.vinayak.project.uber.uberApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {
    private final RideRequestRepo rideRequestRepo;

    @Override
    public RideRequest findRequestById(Long rideRequestId) {
        return rideRequestRepo.findById(rideRequestId)
                .orElseThrow(()-> new ResourceNotFoundException("RideRequest Not Found with the given id"));
    }

    @Override
    public void update(RideRequest rideRequest) {
        rideRequestRepo.findById(rideRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("RideRequest not found with the given id"));

        rideRequestRepo.save(rideRequest);
    }
}
