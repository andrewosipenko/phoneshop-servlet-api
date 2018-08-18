package model;

import java.math.BigDecimal;
import java.util.Currency;

public class Product {
    private Long id;
    private String code;
    private String description;
    private BigDecimal price;
    private Currency currency;
    private Integer stock;

    private static Long counter = 1L;

    public Long getId() {
        return id;
    }

    public void setId() {
        this.id = counter++;
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
