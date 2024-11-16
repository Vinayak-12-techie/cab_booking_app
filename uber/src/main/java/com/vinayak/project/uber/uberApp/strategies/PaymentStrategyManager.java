package com.vinayak.project.uber.uberApp.strategies;

import com.vinayak.project.uber.uberApp.entities.enums.PaymentMethod;
import com.vinayak.project.uber.uberApp.strategies.impl.CODPaymentStrategy;
import com.vinayak.project.uber.uberApp.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CODPaymentStrategy codPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case WALLET -> walletPaymentStrategy;
            case CASH -> codPaymentStrategy;
            default -> throw new RuntimeException("Invalid Payment Method");
        };
    }
}
