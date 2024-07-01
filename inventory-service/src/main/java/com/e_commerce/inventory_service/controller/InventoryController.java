package com.e_commerce.inventory_service.controller;

import com.e_commerce.inventory_service.entity.InventoryProduct;
import com.e_commerce.inventory_service.service.InventoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class InventoryController {
    @Autowired
    private InventoryProductService inventoryProductService;
    @Autowired
    private Environment environment;

    @GetMapping("/api/inventory/port")
    public String getPort()
    {
        return environment.getProperty("local.server.port");
    }

    @PostMapping("api/inventory/product")
    public ResponseEntity<InventoryProduct> addProduct(@RequestBody InventoryProduct product) {
        InventoryProduct savedProduct = inventoryProductService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/api/inventory/product/{id}")
    public ResponseEntity<InventoryProduct> getProduct(@PathVariable Long id) {
        InventoryProduct product = inventoryProductService.getInventoryProductById(id);

        return ResponseEntity.ok(product);

    }

    @GetMapping("/api/inventory/product/by-name/{name}")
    public ResponseEntity<InventoryProduct> getProductByName(@PathVariable String name) {
        InventoryProduct product = inventoryProductService.getInventoryProductByName(name);
        return ResponseEntity.ok(product);

    }

    @GetMapping("/api/inventory/product")
    public ResponseEntity<List<InventoryProduct>> getProduct() {
        List<InventoryProduct> products = inventoryProductService.getInventoryProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/api/inventory/product/{productId}")
    public ResponseEntity<InventoryProduct> updateProduct(@PathVariable Long productId, @RequestBody InventoryProduct product) {
        InventoryProduct updatedProduct = inventoryProductService.updateProduct(productId, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/api/inventory/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            String result = inventoryProductService.deleteProduct(productId);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
