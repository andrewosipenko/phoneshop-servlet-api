package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Product {
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
    private List<PriceHistory> priceHistoryList;

    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        priceHistoryList = new ArrayList<>();
    }

    public Product(long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        priceHistoryList = new ArrayList<>();
    }

    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl, List<PriceHistory> priceHistoryList) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistoryList = priceHistoryList;
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

    public List<PriceHistory> getPriceHistoryList() {
        return priceHistoryList;
    }

    public void setPriceHistoryList(List<PriceHistory> priceHistoryList) {
        this.priceHistoryList = priceHistoryList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (stock != product.stock) return false;
        if (!id.equals(product.id)) return false;
        if (!code.equals(product.code)) return false;
        if (!description.equals(product.description)) return false;
        if (!price.equals(product.price)) return false;
        if (!currency.equals(product.currency)) return false;
        return imageUrl.equals(product.imageUrl);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + currency.hashCode();
        result = 31 * result + stock;
        result = 31 * result + imageUrl.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                ", stock=" + stock +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}