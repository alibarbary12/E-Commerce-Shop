package com.e_commerce.inventory_service.service;

import com.e_commerce.inventory_service.entity.InventoryProduct;
import com.e_commerce.inventory_service.error_response.ProductErrorResponse;
import com.e_commerce.inventory_service.exception.ProductNotFoundException;
import com.e_commerce.inventory_service.proxy.ShopProxy;
import com.e_commerce.inventory_service.repository.InventoryProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;

@Service

public class InventoryProductService {
    @Autowired
    private InventoryProductRepository inventoryProductRepository;
    @Autowired
    private ShopProxy shopProxy;

    public InventoryProduct addProduct(InventoryProduct product) {
        // Validate product details if necessary
        // This can include checking for null values or ensuring uniqueness constraints
        return inventoryProductRepository.save(product);
    }

    public InventoryProduct updateProduct(Long productId, InventoryProduct updatedProduct) {
        Optional<InventoryProduct> productOptional = inventoryProductRepository.findById(productId);
        if(productOptional.isEmpty())
            throw new ProductNotFoundException("product with id:" +productId+ " not found");


        updatedProduct.setId(productId);


        return inventoryProductRepository.save(updatedProduct);
    }

    public InventoryProduct getInventoryProductById(Long id) {
        Optional<InventoryProduct> inventoryProductOptional = inventoryProductRepository.findById(id);
        if(inventoryProductOptional.isEmpty())
            throw new ProductNotFoundException("product with id: "+id+" not found");
        return inventoryProductOptional.get();
    }



    public List<InventoryProduct> getInventoryProducts() {
        return inventoryProductRepository.findAll();
    }

    public String deleteProduct(Long id) {
        shopProxy.deleteProduct(id);
        inventoryProductRepository.deleteById(id);


        return "Product successfully deleted!";
    }

    public InventoryProduct getInventoryProductByName(String name) {

        Optional<InventoryProduct> inventoryProductOptional = inventoryProductRepository.findByName(name);
        if (inventoryProductOptional.isPresent()) {
            return inventoryProductOptional.get();
        } else {
            throw new ProductNotFoundException("Unable to find product with the name: "+name+" in inventory!");
        }
    }
}
