package com.vinayak.project.uber.uberApp.services;

import com.vinayak.project.uber.uberApp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
