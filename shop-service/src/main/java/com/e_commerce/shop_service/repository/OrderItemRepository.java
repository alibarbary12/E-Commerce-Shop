package com.e_commerce.shop_service.repository;

import com.e_commerce.shop_service.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
