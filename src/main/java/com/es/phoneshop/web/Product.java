package com.es.phoneshop.web;

import java.math.BigDecimal;
import java.util.Currency;

public class Product {
    private final Long id;
    private static Long tempId = (long) 0;
    private String code;
    private String description;
    private BigDecimal price;
    private Currency currency;
    private Integer stock;

    public Product(String code, String description, BigDecimal price, Currency currency, Integer stock) {
        id = tempId;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;

        tempId++;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
