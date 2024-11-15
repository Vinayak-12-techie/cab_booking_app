package com.vinayak.project.uber.uberApp.dto;

import com.vinayak.project.uber.uberApp.entities.User;
import com.vinayak.project.uber.uberApp.entities.WalletTransaction;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class WalletDto {

    private Long id;

    private UserDto user;

    private Double balance;

    private List<WalletTransactionDto> transactions;
}
