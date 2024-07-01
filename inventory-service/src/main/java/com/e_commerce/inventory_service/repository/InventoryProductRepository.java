package com.e_commerce.inventory_service.repository;

import com.e_commerce.inventory_service.entity.InventoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryProductRepository extends JpaRepository<InventoryProduct,Long> {


    Optional<InventoryProduct> findByName(String name);
}
