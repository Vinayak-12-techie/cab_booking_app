package com.vinayak.project.uber.uberApp.strategies.impl;

import com.vinayak.project.uber.uberApp.entities.Payment;
import com.vinayak.project.uber.uberApp.strategies.PaymentStrategy;
import org.springframework.stereotype.Service;

@Service
public class WalletPaymentStrategy implements PaymentStrategy {
    @Override
    public void processPayment(Payment payment) {

    }
}
