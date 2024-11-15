package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.dto.WalletDto;
import com.vinayak.project.uber.uberApp.dto.WalletTransactionDto;
import com.vinayak.project.uber.uberApp.entities.WalletTransaction;
import com.vinayak.project.uber.uberApp.repositories.WalletTransactionRepo;
import com.vinayak.project.uber.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepo walletTransactionRepo;
    private final ModelMapper modelMapper;


    @Override
    public void createNewWalletTransaction(WalletTransactionDto walletTransactionDto) {
        WalletTransaction walletTransaction=modelMapper.map(walletTransactionDto,WalletTransaction.class);
        walletTransactionRepo.save(walletTransaction);
    }
}
