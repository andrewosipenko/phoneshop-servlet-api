package com.es.phoneshop.model;

import java.util.Comparator;
import java.util.Currency;
import java.util.List;

public class Product {
    private Long id;
    private String code;
    private String description;
    /** null means there is no price because the product is outdated or new */
    private List<Price> prices;
    /** can be null if the price is null */
    private Currency currency;
    private int stock;
    private String imageUrl;

    public Product() {
    }

    public Product(Long id, String code, String description, List<Price> prices, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.prices = prices;
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

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;

    }

    public Price getCurrentPrice(){
        return this.prices.stream().max(Comparator.comparing(price->price.getDate().toEpochDay())).get();
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
}