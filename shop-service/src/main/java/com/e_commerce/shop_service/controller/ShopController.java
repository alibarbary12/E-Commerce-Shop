package com.e_commerce.shop_service.controller;

import com.e_commerce.shop_service.entity.CartItem;
import com.e_commerce.shop_service.entity.ShoppingCart;
import com.e_commerce.shop_service.proxy.InventoryProxy;
import com.e_commerce.shop_service.service.OrderService;
import com.e_commerce.shop_service.service.ShopService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private InventoryProxy inventoryProxy;

    private Logger logger = LoggerFactory.getLogger(ShopController.class);

    @GetMapping("/api")
    public String portTest() {
        return "the port of this instance is:  " + inventoryProxy.getPort();
    }

    @PostMapping("/api/shoppingCart/cartItem")
    public ResponseEntity<?> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        try {
            ShoppingCart cart = shopService.addToCart(productId, quantity);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding product to cart: " + e.getMessage());
        }

    }

    @DeleteMapping("/api/shoppingCart/cartItem")
    public ResponseEntity<?> deleteFromCart(@RequestParam Long cartItemId) {
        try {
            ShoppingCart cart = shopService.removeFromCart(cartItemId);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing product from cart: " + e.getMessage());
        }
    }

    @GetMapping("/api/shoppingCart/{cartId}")
    public ResponseEntity<?> getCart(@PathVariable Long cartId) {
        try {
            return ResponseEntity.ok(shopService.getCart(cartId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/shoppingCart")
    public ResponseEntity<?> deleteCart() {
        try {
            shopService.removeCart();
            return ResponseEntity.ok("Cart Has Been Removed Successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing cart: " + e.getMessage());
        }

    }


    @GetMapping("/api/order/{cartId}")
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallBackResponse")
    public ResponseEntity<?> makeOrder(@PathVariable Long cartId) {
        logger.info("sample api call received");
        ShoppingCart shoppingCart = shopService.getCart(cartId);
        if(shoppingCart==null||shoppingCart.getCartItems()==null)
            throw new RuntimeException("sorry,your shopping cart is empty");
        return orderService.createOrder(shoppingCart);

    }
    public ResponseEntity<String> fallBackResponse(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());

    }


}
