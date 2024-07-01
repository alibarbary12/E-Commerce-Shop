package com.e_commerce.shop_service.entity;

import java.util.List;

public class OrderRequest {
    private List<CartItem> orderItems;

    public OrderRequest(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "orderItems=" + orderItems +
                '}';
    }
}
