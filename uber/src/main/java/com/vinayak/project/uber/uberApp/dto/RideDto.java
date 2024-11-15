package com.vinayak.project.uber.uberApp.dto;

import com.vinayak.project.uber.uberApp.entities.Driver;
import com.vinayak.project.uber.uberApp.entities.enums.PaymentMethod;
import com.vinayak.project.uber.uberApp.entities.enums.RideStatus;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
public class RideDto {

    private Long id;
    private PointDto pickUpLocation;
    private PointDto dropOffLocation;
    private LocalDateTime createdTime;
    private RiderDto rider;
    private DriverDto driver;
    private PaymentMethod paymentMethod;
    private RideStatus rideStatus ;
    private String otp;
    private double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
