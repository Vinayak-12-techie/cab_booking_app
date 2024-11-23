package com.vinayak.project.uber.uberApp.controllers;

import com.vinayak.project.uber.uberApp.dto.*;
import com.vinayak.project.uber.uberApp.services.RiderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rider")
@Secured("ROLE_RIDER")
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

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(riderService.cancelRide(rideId));
    }

    @PostMapping("/rateDriver")
    public ResponseEntity<DriverDto> rateDriver(@RequestBody RatingDto ratingDto){
        return ResponseEntity.ok(riderService.rateDriver(ratingDto.getRideId(),ratingDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<RiderDto> getMyProfile(){
        return ResponseEntity.ok(riderService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
                                                       @RequestParam(defaultValue = "10", required = false) Integer pageSize){

        PageRequest pageRequest =PageRequest.of(pageOffset,pageSize, Sort.by(Sort.Direction.DESC,"createdTime"));
        return ResponseEntity.ok(riderService.getAllMyRides(pageRequest));
    }
}
