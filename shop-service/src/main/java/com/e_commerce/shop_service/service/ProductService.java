package com.e_commerce.shop_service.service;

import com.e_commerce.shop_service.dto.InventoryProductDto;
import com.e_commerce.shop_service.entity.Product;
import com.e_commerce.shop_service.exception.ProductNotFoundException;
import com.e_commerce.shop_service.proxy.InventoryProxy;
import com.e_commerce.shop_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private InventoryProxy inventoryProxy;

    public ResponseEntity<?> createProduct(Product product) {
        // Additional validation can be added here
        try {
            String productName=product.getName();
            ResponseEntity<InventoryProductDto> responseProduct = inventoryProxy.getProductByName(productName);
            InventoryProductDto inventoryProduct = responseProduct.getBody();
            product.setId(inventoryProduct.getId());
            productRepository.save(product);
            return ResponseEntity.ok(product);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    public Product updateProduct(Long productId, Product updatedProduct) {


        updatedProduct.setId(productId);
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty())
            throw new ProductNotFoundException("unable to find the specified product!");
        Product result = productRepository.save(updatedProduct);
        if (result == null)
            throw new RuntimeException("Unable to update the product!");
        return updatedProduct;

    }

    public List<Product> listProducts() {
        String userName = userDetailsService.getCurrentUsername();
        return productRepository.findAll();
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product getProductById(Long id) {

            Optional<Product> productOptional= productRepository.findById(id);
            if(productOptional.isEmpty())
                throw new ProductNotFoundException("Unable to find product with id "+id);
            return productOptional.get();

    }
}
