package com.e_commerce.inventory_service.entity;

import jakarta.persistence.*;

@Entity
public class InventoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String location;
    private String supplierDetails;
    private double costPrice;
    private int restockThreshold;
    private int stock;


    public InventoryProduct() {
    }

    public InventoryProduct(String location,String name, String supplierDetails, double costPrice, int restockThreshold, int stock) {
        this.location = location;
        this.supplierDetails = supplierDetails;
        this.costPrice = costPrice;
        this.restockThreshold = restockThreshold;
        this.stock = stock;
    }

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

    @Override
    public String toString() {
        return "InventoryProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", supplierDetails='" + supplierDetails + '\'' +
                ", costPrice=" + costPrice +
                ", restockThreshold=" + restockThreshold +
                ", stock=" + stock +
                '}';
    }
}
