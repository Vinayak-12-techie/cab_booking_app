package com.vinayak.project.uber.uberApp.repositories;

import com.vinayak.project.uber.uberApp.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RideRequestRepo extends JpaRepository<RideRequest,Long> {
}
