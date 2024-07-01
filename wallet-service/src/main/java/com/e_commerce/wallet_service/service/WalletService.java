package com.e_commerce.wallet_service.service;

import com.e_commerce.wallet_service.dto.WalletDto;
import com.e_commerce.wallet_service.entity.Transaction;
import com.e_commerce.wallet_service.entity.Wallet;
import com.e_commerce.wallet_service.repository.TransactionRepository;
import com.e_commerce.wallet_service.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    @Autowired
    WalletRepository repository;
    @Autowired
    TransactionRepository transactionRepository;
    public Wallet save(Wallet wallet){
        return repository.save(wallet);
    }
    public ResponseEntity<?> findByUserName(String userName){
        Optional<Wallet> wallet= Optional.ofNullable(repository.findByUserUsername(userName));
        if(wallet.isPresent()){
            return ResponseEntity.ok(wallet.get());
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
        }
    }
    public Wallet deposit(String username, long walletId, double amount){
        Optional<Wallet> walletEntity= Optional.ofNullable(repository.findByUserUsername(username));

            Wallet wallet=walletEntity.get();
            wallet.setBalance(wallet.getBalance()+amount);
            Transaction transaction=new Transaction(wallet,"DEPOSIT",amount, LocalDateTime.now());
            transactionRepository.save(transaction);
            List transactionHistory=wallet.getTransactionHistory();
            transactionHistory.add(transaction);
            wallet.setTransactionHistory(transactionHistory);
            repository.save(wallet);
            return wallet;
    }

    public Wallet withdraw(String username,long walletId,double amount){
        Optional<Wallet> walletEntity= Optional.ofNullable(repository.findByUserUsername(username));
        Wallet wallet=walletEntity.get();
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds for the withdrawal");
        }
        wallet.setBalance(wallet.getBalance()-amount);
        Transaction transaction = new Transaction(wallet,"WITHDRAW",amount,LocalDateTime.now());
        transaction.setWallet(wallet);
        transaction.setAmount(-amount); // Negative because it's a withdrawal
        transactionRepository.save(transaction);
        List transactionHistory=wallet.getTransactionHistory();
        transactionHistory.add(transaction);
        wallet.setTransactionHistory(transactionHistory);
        repository.save(wallet);
        return wallet;




    }



}
