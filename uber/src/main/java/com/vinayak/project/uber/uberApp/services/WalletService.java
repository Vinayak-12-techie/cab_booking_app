package com.vinayak.project.uber.uberApp.services;

import com.vinayak.project.uber.uberApp.entities.User;
import com.vinayak.project.uber.uberApp.entities.Wallet;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount);
    Wallet deductMoneyFromWallet(User user, Double amount)
    void withdrawAllMyMoneyFromWallet();
    Wallet findByWalletById(Long walletId);
    Wallet createNewWallet(User user);
    Wallet findByUser(User user);
}
