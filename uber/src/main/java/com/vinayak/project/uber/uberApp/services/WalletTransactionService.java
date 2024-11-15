package com.vinayak.project.uber.uberApp.services;

import com.vinayak.project.uber.uberApp.dto.WalletTransactionDto;
import com.vinayak.project.uber.uberApp.entities.WalletTransaction;

public interface WalletTransactionService {
     void createNewWalletTransaction(WalletTransactionDto walletTransactionDto);

}
