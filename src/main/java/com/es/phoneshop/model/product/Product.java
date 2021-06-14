package com.es.phoneshop.model.product;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
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
    private List<PriceHistory> priceHistory = new ArrayList<>();

    public Product() {
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock,
                   String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistory.add(new PriceHistory(LocalDate.now(), price));
    }

    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = null;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistory.add(new PriceHistory(LocalDate.now(), price));
    }

    public void setPrice(@NonNull final BigDecimal price) {
        this.price = price;
        this.priceHistory.add(new PriceHistory(LocalDate.now(), price));
    }
}