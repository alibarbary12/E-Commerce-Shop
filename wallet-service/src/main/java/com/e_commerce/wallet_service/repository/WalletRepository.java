package com.e_commerce.wallet_service.repository;

import com.e_commerce.wallet_service.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    public Wallet findByUserUsername(String userName);



}
