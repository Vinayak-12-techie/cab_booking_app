package com.vinayak.project.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
@Builder
@Table(indexes = {
        @Index(name = "idx_vehicle_id",columnList = "vehicleId")
})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    private double rating;

    private String vehicleId;

    private Boolean available;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;

}
