package com.vinayak.project.uber.uberApp.repositories;

import com.vinayak.project.uber.uberApp.entities.Driver;
import com.vinayak.project.uber.uberApp.entities.Ride;
import com.vinayak.project.uber.uberApp.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RideRepo extends JpaRepository<Ride,Long> {
    Page<Ride> findByRider(Rider rider, Pageable pageRequest);

    Page<Ride> findByDriver(Driver driver, Pageable pageRequest);
}
