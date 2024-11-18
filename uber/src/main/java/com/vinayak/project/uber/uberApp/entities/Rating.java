package com.vinayak.project.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Table(indexes = {
        @Index(name = "idx_rating_rider" ,columnList = "rider_id"),
        @Index(name = "idx_rating_driver" ,columnList = "driver_id")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Ride ride;

    @ManyToOne
    private Rider rider;

    @ManyToOne
    private Driver driver;

    private Integer driverRating; //Rating for the Driver
    private Integer riderRating; //Rating for the Rider
}
