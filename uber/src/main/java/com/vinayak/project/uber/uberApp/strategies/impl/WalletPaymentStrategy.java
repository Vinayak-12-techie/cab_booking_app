package com.vinayak.project.uber.uberApp.strategies.impl;

import com.vinayak.project.uber.uberApp.entities.Driver;
import com.vinayak.project.uber.uberApp.entities.Payment;
import com.vinayak.project.uber.uberApp.entities.Rider;
import com.vinayak.project.uber.uberApp.entities.enums.PaymentStatus;
import com.vinayak.project.uber.uberApp.entities.enums.TransactionMethod;
import com.vinayak.project.uber.uberApp.repositories.PaymentRepo;
import com.vinayak.project.uber.uberApp.services.PaymentService;
import com.vinayak.project.uber.uberApp.services.WalletService;
import com.vinayak.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Rider has 232, Driver has 500
//Ride cost is 100, commission = 30
//Rider->232-100=132
//Driver -> 500+(100-30) =570

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
//    private final PaymentService paymentService;
    private final PaymentRepo paymentRepo;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver=payment.getRide().getDriver();
        Rider rider= payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null, payment.getRide(), TransactionMethod.RIDE);

        double driverCut= payment.getAmount()*(1-PLATFORM_COMMISSION);
        walletService.addMoneyToWallet(driver.getUser(), driverCut, null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        paymentRepo.save(payment);

    }
}
