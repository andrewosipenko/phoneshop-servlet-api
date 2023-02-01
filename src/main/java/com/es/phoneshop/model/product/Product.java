package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;

public class Product implements Serializable {
    private Long id;
    private String code;
    private String description;
    /**
     * null means there is no price because the product is outdated or new
     */
    private BigDecimal price;
    /**
     * can be null if the price is null
     */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private Map<Date, BigDecimal> priceHistory;

    private static final long serialVersionUID = 173827L;

    public Map<Date, BigDecimal> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(HashMap<Date, BigDecimal> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public Product() {
        priceHistory = new HashMap<>();
    }

    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        priceHistory = new HashMap<>();
        putPriceHistory(price);
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistory = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        putPriceHistory(price);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private void putPriceHistory(BigDecimal price) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        Calendar cals = Calendar.getInstance();
        Date date = cals.getTime();
        priceHistory.put(date, price);
    }
}