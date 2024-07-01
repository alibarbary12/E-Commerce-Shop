package com.e_commerce.shop_service.service;

import com.e_commerce.shop_service.dto.InventoryProductDto;
import com.e_commerce.shop_service.entity.*;
import com.e_commerce.shop_service.exception.InsufficientFundsException;
import com.e_commerce.shop_service.proxy.InventoryProxy;
import com.e_commerce.shop_service.proxy.WalletProxy;
import com.e_commerce.shop_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private InventoryProxy inventoryProxy;
    @Autowired
    private WalletProxy walletProxy;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShopService shopService;

    public ResponseEntity<?> createOrder(ShoppingCart shoppingCart) {
        String username = userDetailsService.getCurrentUsername();
        MyUser user = userDetailsService.loadMyUserByUsername(username);
        long userId = user.getId();
        List<Product> products = new ArrayList<Product>();
        List<CartItem> cartItems=shoppingCart.getCartItems();
        //this step is to get the stock of the products in the shopping cart
        Order order = new Order(user, 0, LocalDateTime.now(), new ArrayList<OrderItem>());
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem currentCartItem = cartItems.get(i);
            Long currentProductId = currentCartItem.getProduct().getId();
            InventoryProductDto currentInventoryProduct = inventoryProxy.getProduct(currentProductId).getBody();
            String productName = currentCartItem.getProduct().getName();
            long productPrice = (long)currentCartItem.getProduct().getPrice();
            int productStock = currentInventoryProduct.getStock();
            OrderItem currentOrderItem = new OrderItem(productName, currentCartItem.getQuantity(), order);
            List<OrderItem> orderItems = order.getItems();
            orderItems.add(currentOrderItem);
            order.setItems(orderItems);


        }
        order.setTotal(shoppingCart.getTotal());
        ResponseEntity<?> walletResponse = walletProxy.withdraw((long)shoppingCart.getTotal());
        if (walletResponse.getStatusCode() == HttpStatus.OK) {
            orderRepository.save(order);
            shopService.removeCart();
            return ResponseEntity.ok(order);

        }
    throw new InsufficientFundsException("Insufficient funds for this order!");
    }







    }


