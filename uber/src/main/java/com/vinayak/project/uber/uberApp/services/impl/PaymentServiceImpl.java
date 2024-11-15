package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.entities.Payment;
import com.vinayak.project.uber.uberApp.entities.Ride;
import com.vinayak.project.uber.uberApp.repositories.PaymentRepo;
import com.vinayak.project.uber.uberApp.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepo paymentRepo;

    @Override
    public void processPayment(Payment payment) {

    }

    @Override
    public Payment createNewPayment(Ride ride) {
        return null;
    }
}
