package com.vinayak.project.uber.uberApp.repositories;

import com.vinayak.project.uber.uberApp.entities.Ride;
import com.vinayak.project.uber.uberApp.entities.Rider;
import com.vinayak.project.uber.uberApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepo extends JpaRepository<Rider,Long> {
    Optional<Rider> findByUser(User user);
}
