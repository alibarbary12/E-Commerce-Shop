package com.e_commerce.shop_service.service;

import com.e_commerce.shop_service.dto.InventoryProductDto;
import com.e_commerce.shop_service.entity.CartItem;
import com.e_commerce.shop_service.entity.MyUser;
import com.e_commerce.shop_service.entity.Product;
import com.e_commerce.shop_service.entity.ShoppingCart;
import com.e_commerce.shop_service.proxy.InventoryProxy;
import com.e_commerce.shop_service.repository.CartItemRepository;
import com.e_commerce.shop_service.repository.OrderRepository;
import com.e_commerce.shop_service.repository.ProductRepository;
import com.e_commerce.shop_service.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private InventoryProxy inventoryProxy;


    public ShoppingCart addToCart(Long productId, int quantity) {
        String username = userDetailsService.getCurrentUsername();
        MyUser user = userDetailsService.loadMyUserByUsername(username);
        long userId = user.getId();
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (cart == null) {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUserId(userId);
            shoppingCartRepository.save(newCart);
            cart = newCart;
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty())
            throw new RuntimeException("Product Not Found!");
        Product product = productOptional.get();
        ResponseEntity<InventoryProductDto> inventoryProductResponse = inventoryProxy.getProduct(product.getId());
        InventoryProductDto inventoryProduct = inventoryProductResponse.getBody();
        if (inventoryProduct.getStock() < quantity)
            throw new RuntimeException("Insufficient Stock For Product!");
        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId() == (productId)).findFirst();
        if (existingCartItem.isPresent()) {
            // Update quantity of existing cart item
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotal(cartItem.getTotal() + (product.getPrice() * quantity));
            cartItemRepository.save(cartItem);
            cart.updateCartTotal();
        } else {
            // Add new item to the cart
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setTotal(product.getPrice()*quantity);
            newItem.setShoppingCart(cart);
            cartItemRepository.save(newItem);
            cart.getCartItems().add(newItem); // Ensure the cart is aware of the new item
            cart.updateCartTotal();
        }

        inventoryProduct.setStock(inventoryProduct.getStock() - quantity);
        productRepository.save(product);
        shoppingCartRepository.save(cart);
        inventoryProxy.updateProduct(product.getId(), inventoryProduct);

        return cart;
    }

    public ShoppingCart removeFromCart(Long cartItemId) {
        String username = userDetailsService.getCurrentUsername();
        MyUser user = userDetailsService.loadMyUserByUsername(username);
        long userId = user.getId();
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if(cart==null)
            throw new RuntimeException("Cart was not found!");
        List<CartItem> cartItems = cart.getCartItems();
        for (int i = 0; i < cartItems.size(); i++) {
            Product product = cartItems.get(i).getProduct();
            CartItem cartItem = cartItems.get(i);
            ResponseEntity<InventoryProductDto> inventoryProductResponse = inventoryProxy.getProduct(product.getId());
            InventoryProductDto inventoryProduct = inventoryProductResponse.getBody();
            if (cartItems.get(i).getId() == cartItemId) {
                System.out.println(cartItems.get(i).getProduct().getName());
                cartItems.remove(i);
                cartItemRepository.deleteById(cartItemId);
                inventoryProduct.setStock(inventoryProduct.getStock() + cartItem.getQuantity());
                productRepository.save(product);
                inventoryProxy.updateProduct(inventoryProduct.getId(),inventoryProduct);
            }

        }
        cart.setItems(cartItems);
        cart.updateCartTotal();
        shoppingCartRepository.save(cart);
        return cart;
    }

    public void removeCart() {
        String username = userDetailsService.getCurrentUsername();
        MyUser user = userDetailsService.loadMyUserByUsername(username);
        long userId = user.getId();
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        shoppingCartRepository.delete(cart);
        List<CartItem> cartItems = cart.getCartItems();
        for (int i = 0; i < cartItems.size(); i++) {
            Product product = cartItems.get(i).getProduct();
            CartItem cartItem = cartItems.get(i);
            ResponseEntity<InventoryProductDto> inventoryProductResponse = inventoryProxy.getProduct(product.getId());
            InventoryProductDto inventoryProduct = inventoryProductResponse.getBody();
            inventoryProduct.setStock(inventoryProduct.getStock() + cartItem.getQuantity());
            productRepository.save(product);

        }


    }

    public ShoppingCart getCart(Long cartId) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartId);
        if (shoppingCartOptional.isEmpty())
            throw new RuntimeException("Cart was not found!");
        return shoppingCartOptional.get();

    }


}
