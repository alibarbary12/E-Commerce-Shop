package com.e_commerce.wallet_service.dto;

import com.e_commerce.wallet_service.entity.Transaction;

import java.time.LocalDateTime;

public class TransactionDto {
    private int id;
    private long walletId;
    private String type;
    private Double amount;
    private LocalDateTime date;
    public TransactionDto(Transaction transaction){
        this.id=transaction.getId();
        this.walletId=transaction.getWallet().getId();
        this.type=transaction.getType();
        this.amount=transaction.getAmount();
        this.date=transaction.getDate();

    }
}
