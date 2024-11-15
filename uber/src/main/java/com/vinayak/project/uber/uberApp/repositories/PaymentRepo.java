package com.vinayak.project.uber.uberApp.repositories;

import com.vinayak.project.uber.uberApp.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {
}
