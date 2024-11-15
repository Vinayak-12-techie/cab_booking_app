package com.vinayak.project.uber.uberApp.services;

import com.vinayak.project.uber.uberApp.entities.Payment;
import com.vinayak.project.uber.uberApp.entities.Ride;

public interface PaymentService {
    void processPayment(Payment payment);

    Payment createNewPayment(Ride ride);
}
