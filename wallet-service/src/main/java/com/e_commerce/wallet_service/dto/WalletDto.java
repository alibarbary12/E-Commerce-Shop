package com.e_commerce.wallet_service.dto;

import com.e_commerce.wallet_service.entity.Transaction;
import com.e_commerce.wallet_service.entity.Wallet;

import java.util.List;

public class WalletDto {
    private long id;
    private long userId;
    private double balance;
    private List<Transaction> transactionHistory;
    public WalletDto(Wallet wallet){
        this.id=wallet.getId();
        this.userId=wallet.getUser().getId();
        this.balance=wallet.getBalance();
        this.transactionHistory=wallet.getTransactionHistory();
    }

    public WalletDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}
