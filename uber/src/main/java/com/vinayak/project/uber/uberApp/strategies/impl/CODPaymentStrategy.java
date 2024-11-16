package com.vinayak.project.uber.uberApp.strategies.impl;

import com.vinayak.project.uber.uberApp.entities.Driver;
import com.vinayak.project.uber.uberApp.entities.Payment;
import com.vinayak.project.uber.uberApp.entities.Wallet;
import com.vinayak.project.uber.uberApp.entities.enums.PaymentStatus;
import com.vinayak.project.uber.uberApp.entities.enums.TransactionMethod;
import com.vinayak.project.uber.uberApp.repositories.PaymentRepo;
import com.vinayak.project.uber.uberApp.services.PaymentService;
import com.vinayak.project.uber.uberApp.services.WalletService;
import com.vinayak.project.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


//Rider gives 100
//Driver have to give 30 to App, so will deduct 30 from driver's wallet

@Service
@RequiredArgsConstructor
public class CODPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
//    private final PaymentService paymentService;
    private final PaymentRepo paymentRepo;

    @Override
    public void processPayment(Payment payment) {
        Driver driver=payment.getRide().getDriver();

        double platformCommission= payment.getAmount()*PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(),platformCommission,null,payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        paymentRepo.save(payment);
    }
}
