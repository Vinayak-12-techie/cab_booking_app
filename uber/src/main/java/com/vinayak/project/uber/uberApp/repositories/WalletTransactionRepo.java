package com.vinayak.project.uber.uberApp.repositories;

import com.vinayak.project.uber.uberApp.entities.Wallet;
import com.vinayak.project.uber.uberApp.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepo extends JpaRepository<WalletTransaction,Long> {
}
