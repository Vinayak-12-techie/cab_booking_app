package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.dto.RideDto;
import com.vinayak.project.uber.uberApp.dto.WalletDto;
import com.vinayak.project.uber.uberApp.dto.WalletTransactionDto;
import com.vinayak.project.uber.uberApp.entities.Ride;
import com.vinayak.project.uber.uberApp.entities.User;
import com.vinayak.project.uber.uberApp.entities.Wallet;
import com.vinayak.project.uber.uberApp.entities.WalletTransaction;
import com.vinayak.project.uber.uberApp.entities.enums.TransactionMethod;
import com.vinayak.project.uber.uberApp.entities.enums.TransactionType;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.repositories.WalletRepo;
import com.vinayak.project.uber.uberApp.services.WalletService;
import com.vinayak.project.uber.uberApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepo walletRepo;
    private final ModelMapper modelMapper;
    private final WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet= findByUser(user);
        wallet.setBalance(wallet.getBalance()+amount);

        WalletTransaction walletTransaction= WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);
        return walletRepo.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet= findByUser(user);

        wallet.setBalance(wallet.getBalance()-amount);

        WalletTransaction walletTransaction= WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

//        walletTransactionService.createNewWalletTransaction(walletTransaction);

        wallet.getTransactions().add(walletTransaction);
        return walletRepo.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findByWalletById(Long walletId) {
        return walletRepo.findById(walletId)
                .orElseThrow(()->new ResourceNotFoundException("Wallet not found with the given Id : "+walletId));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet =new Wallet();
        wallet.setUser(user);
        return walletRepo.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepo.findByUser(user)
                .orElseThrow(()->new ResourceNotFoundException("No wallet found associated with the user"));
    }
}
