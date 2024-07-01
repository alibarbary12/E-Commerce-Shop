package com.e_commerce.shop_service.proxy;

import com.e_commerce.shop_service.dto.InventoryProductDto;
import com.e_commerce.shop_service.entity.Product;
import com.e_commerce.shop_service.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "inventory-service",configuration = FeignClientConfig.class)
public interface InventoryProxy {
    @GetMapping("/api/inventory/product/{id}")
    public ResponseEntity<InventoryProductDto> getProduct(@PathVariable Long id);

    @GetMapping("/api/inventory/product/by-name/{name}")
    public ResponseEntity<InventoryProductDto> getProductByName(@PathVariable("name") String productName);

    @GetMapping("/api/port")
    public String getPort();

    @PutMapping("/api/inventory/product/{productId}")
    public ResponseEntity<InventoryProductDto> updateProduct(@PathVariable Long productId, @RequestBody InventoryProductDto product);


}
