package com.vinayak.project.uber.uberApp.controllers;

import com.vinayak.project.uber.uberApp.dto.RideRequestDto;
import com.vinayak.project.uber.uberApp.services.RiderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rider")
public class RiderController {

    private final RiderService riderService;


    //Constructor injection but can also be used using @RequiredArsConstructor of lombok
    public RiderController(RiderService riderService) {
        this.riderService = riderService;
    }

    @PostMapping("/requestRide")
    //RideRequest DTO that is passed inside the function will not contain all the info , but the DTO that is to be returned from this function will contain all the info
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto){
        return ResponseEntity.ok(riderService.requestRide(rideRequestDto));
    }
}
