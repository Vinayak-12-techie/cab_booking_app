package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.entities.Payment;
import com.vinayak.project.uber.uberApp.entities.Ride;
import com.vinayak.project.uber.uberApp.entities.enums.PaymentStatus;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.repositories.PaymentRepo;
import com.vinayak.project.uber.uberApp.services.PaymentService;
import com.vinayak.project.uber.uberApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepo paymentRepo;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(Ride ride) {
        Payment payment=paymentRepo.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("Payment not for Ride : "+ ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment= Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        return paymentRepo.save(payment);
    }

//    @Override
//    public void updAtePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
//        payment.setPaymentStatus(paymentStatus);
//        paymentRepo.save(payment);
//    }
}
