package com.e_commerce.shop_service.repository;

import com.e_commerce.shop_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
