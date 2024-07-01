package com.e_commerce.wallet_service.controller;

import com.e_commerce.wallet_service.dto.TransactionRequestDto;
import com.e_commerce.wallet_service.dto.WalletDto;
import com.e_commerce.wallet_service.entity.Transaction;
import com.e_commerce.wallet_service.entity.MyUser;
import com.e_commerce.wallet_service.entity.Wallet;
import com.e_commerce.wallet_service.service.CustomUserDetailsService;
import com.e_commerce.wallet_service.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WalletController {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private WalletService walletService;

    @PostMapping("/api/wallet")
    public ResponseEntity<Wallet> createWallet() {
        String userName = userDetailsService.getCurrentUsername();
        MyUser user = userDetailsService.loadMyUserByUsername(userName);
        Wallet createdWallet = new Wallet(user, 0.0, new ArrayList<Transaction>());
        walletService.save(createdWallet);
        return ResponseEntity.ok(createdWallet);

    }

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getWallet() {
        String userName = userDetailsService.getCurrentUsername();
        return (ResponseEntity<Wallet>) walletService.findByUserName(userName);
    }
    @PostMapping("/api/wallet/deposit")
    public ResponseEntity<Wallet> deposit(@RequestBody TransactionRequestDto request){
        String userName=userDetailsService.getCurrentUsername();
        ResponseEntity<?> walletEntity=walletService.findByUserName(userName);
        Wallet wallet= (Wallet) walletEntity.getBody();
        wallet=walletService.deposit(userName,wallet.getId(),request.getAmount());
        return ResponseEntity.ok(wallet);
    }
    @GetMapping("/api/wallet/withdraw/{amount}")
    public ResponseEntity<String> withdraw(@PathVariable long amount){
        String userName=userDetailsService.getCurrentUsername();
        ResponseEntity<?> walletEntity=walletService.findByUserName(userName);
        Wallet wallet= (Wallet) walletEntity.getBody();
        try{
        wallet=walletService.withdraw(userName,wallet.getId(),amount);
        return ResponseEntity.ok("successful withdrawal!");}
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/api/wallet/transactionhistory")
    public ResponseEntity<List<Transaction>> transactionHistory(){
        String userName=userDetailsService.getCurrentUsername();
        ResponseEntity<?> walletEntity=walletService.findByUserName(userName);
        Wallet wallet= (Wallet) walletEntity.getBody();
        List<Transaction> history=wallet.getTransactionHistory();
        return ResponseEntity.ok(history);

    }

//    @GetMapping("/api")
//    public String test(){
//        String username= SecurityConfiguration.getCurrentUsername();
//        return username;
//    }

}
