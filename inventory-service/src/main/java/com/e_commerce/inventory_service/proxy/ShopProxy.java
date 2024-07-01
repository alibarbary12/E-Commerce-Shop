package com.e_commerce.inventory_service.proxy;

import com.e_commerce.inventory_service.entity.InventoryProduct;
import com.e_commerce.inventory_service.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="shop-service",configuration = FeignClientConfig.class)
public interface ShopProxy {
    @GetMapping("/api/product/{id}")
    public ResponseEntity<InventoryProduct> getProduct(@PathVariable Long id);
    @DeleteMapping("/api/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id);
}
