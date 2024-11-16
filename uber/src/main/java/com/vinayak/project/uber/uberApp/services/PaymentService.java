package com.vinayak.project.uber.uberApp.services;

import com.vinayak.project.uber.uberApp.entities.Payment;
import com.vinayak.project.uber.uberApp.entities.Ride;
import com.vinayak.project.uber.uberApp.entities.enums.PaymentStatus;

public interface PaymentService {
    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

//    void updAtePaymentStatus(Payment payment,PaymentStatus paymentStatus);
}
