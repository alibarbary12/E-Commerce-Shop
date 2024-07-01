package com.e_commerce.shop_service.dto;

public class InventoryProductDto {
    private Long id;
    private String location;
    private String supplierDetails;
    private double costPrice;
    private int restockThreshold;
    private int stock;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSupplierDetails() {
        return supplierDetails;
    }

    public void setSupplierDetails(String supplierDetails) {
        this.supplierDetails = supplierDetails;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public int getRestockThreshold() {
        return restockThreshold;
    }

    public void setRestockThreshold(int restockThreshold) {
        this.restockThreshold = restockThreshold;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
