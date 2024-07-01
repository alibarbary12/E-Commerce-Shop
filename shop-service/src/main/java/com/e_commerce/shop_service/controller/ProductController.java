package com.e_commerce.shop_service.controller;

import com.e_commerce.shop_service.dto.InventoryProductDto;
import com.e_commerce.shop_service.entity.Product;
import com.e_commerce.shop_service.proxy.InventoryProxy;
import com.e_commerce.shop_service.proxy.WalletProxy;
import com.e_commerce.shop_service.service.CustomUserDetailsService;
import com.e_commerce.shop_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private WalletProxy walletProxy;
    @Autowired
    private InventoryProxy inventoryProxy;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/api/product")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {

        ResponseEntity<?> createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/api/product/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);

    }

    @GetMapping("/api/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/api/product")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.listProducts();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/api/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("product deleted successfully!");
    }

    @GetMapping("api/feign/product/{id}")
    public ResponseEntity<?> getProductFromInventory(@PathVariable Long id) {
        String userName = userDetailsService.getCurrentUsername();
        try {
            return ResponseEntity.ok(inventoryProxy.getProduct(id).getBody());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("product was not found!");


        }

    }
}
