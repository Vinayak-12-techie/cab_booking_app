package com.vinayak.project.uber.uberApp.dto;

import com.vinayak.project.uber.uberApp.entities.Rider;
import com.vinayak.project.uber.uberApp.entities.enums.PaymentMethod;
import com.vinayak.project.uber.uberApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {

    private Long id;
    private PointDto pickUpLocation;
    private PointDto dropOffLocation;
    private PaymentMethod paymentMethod;
    private LocalDateTime requestedTime;
    private RiderDto rider;
    private RideRequestStatus rideRequestStatus;
    private Double fare;
}
