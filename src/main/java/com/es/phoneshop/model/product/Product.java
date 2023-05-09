package com.es.phoneshop.model.product;

import com.es.phoneshop.price.PriceHistory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import java.util.Objects;

public class Product {

    private long id;
    private String code;
    private String description;
    /** null means there is no price because the product is outdated or new */
    private BigDecimal price;
    /** can be null if the price is null */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private List<PriceHistory> priceHistory;

    public Product() {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.getMostSignificantBits();
        this.code = "";
        this.description = "";
        this.price = BigDecimal.ZERO;
        this.currency = Currency.getInstance("USD");
        this.stock = 0;
        this.imageUrl = "";
        this.priceHistory = new ArrayList<>();
        priceHistory.add(new PriceHistory(price, new Date()));
    }

    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.getMostSignificantBits();
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistory = new ArrayList<>();
        priceHistory.add(new PriceHistory(price, new Date()));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        priceHistory.add(new PriceHistory(price, new Date()));
        this.price = price;
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

    public List<PriceHistory> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PriceHistory> priceHistory) {
        this.priceHistory = priceHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}