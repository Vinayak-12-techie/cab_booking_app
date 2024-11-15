package com.vinayak.project.uber.uberApp.services.impl;

import com.vinayak.project.uber.uberApp.dto.WalletTransactionDto;
import com.vinayak.project.uber.uberApp.entities.User;
import com.vinayak.project.uber.uberApp.entities.Wallet;
import com.vinayak.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.vinayak.project.uber.uberApp.repositories.WalletRepo;
import com.vinayak.project.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepo walletRepo;

    @Override
    public Wallet addMoneyToWallet(User user, Double amount) {
        Wallet wallet= findByUser(user);

        wallet.setBalance(wallet.getBalance()+amount);

        return walletRepo.save(wallet);
    }

    @Override
    public Wallet deductMoneyFromWallet(User user, Double amount) {
        Wallet wallet= findByUser(user);

        wallet.setBalance(wallet.getBalance()-amount);
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
