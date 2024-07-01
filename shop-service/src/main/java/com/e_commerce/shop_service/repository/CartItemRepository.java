package com.e_commerce.shop_service.repository;

import com.e_commerce.shop_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {


}
