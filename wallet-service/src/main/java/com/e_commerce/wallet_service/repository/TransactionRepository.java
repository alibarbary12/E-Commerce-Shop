package com.e_commerce.wallet_service.repository;

import com.e_commerce.wallet_service.entity.MyUser;
import com.e_commerce.wallet_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

}
