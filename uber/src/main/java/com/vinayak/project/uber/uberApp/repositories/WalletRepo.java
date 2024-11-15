package com.vinayak.project.uber.uberApp.repositories;

import com.vinayak.project.uber.uberApp.entities.User;
import com.vinayak.project.uber.uberApp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepo extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByUser(User user);
}
