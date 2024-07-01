package com.e_commerce.shop_service.repository;

import com.e_commerce.shop_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
