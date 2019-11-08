package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;

public class Product {
    private Long id;
    private String code;
    private String description;
    /** null means there is no price because the product is outdated or new */
    private Map<Date, BigDecimal> prices = new LinkedHashMap<>();
    /** can be null if the price is null */
    private Currency currency;
    private int stock;
    private String imageUrl;

    public Product() {
    }

    public Product(Long id, String code, String description, Map<Date, BigDecimal> prices, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.prices.putAll(prices);
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
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

    public Map<Date, BigDecimal> getPrices() {
        return prices;
    }

    public BigDecimal getPrice() {
        return (BigDecimal) (prices.values().toArray())[prices.size() - 1];
    }

    public void setPrices(Map<Date, BigDecimal> price) {
        this.prices.putAll(price);
    }

    public void setPrice(Date date, BigDecimal price) {
        this.prices.put(date, price);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return stock == product.stock &&
                Objects.equals(id, product.id) &&
                Objects.equals(code, product.code) &&
                Objects.equals(description, product.description) &&
                Objects.equals(prices, product.prices) &&
                Objects.equals(currency, product.currency) &&
                Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, description, prices, currency, stock, imageUrl);
    }
}