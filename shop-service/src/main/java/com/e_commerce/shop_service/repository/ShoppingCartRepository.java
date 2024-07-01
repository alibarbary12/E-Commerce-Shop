package com.e_commerce.shop_service.repository;

import com.e_commerce.shop_service.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    ShoppingCart findByUserId(long userId);

}
