package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.entities.WalletTransaction;
import com.vinayak.project.uber.uberApp.repositories.WalletTransactionRepo;
import com.vinayak.project.uber.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepo walletTransactionRepo;
    private final ModelMapper modelMapper;


    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepo.save(walletTransaction);
    }
}
