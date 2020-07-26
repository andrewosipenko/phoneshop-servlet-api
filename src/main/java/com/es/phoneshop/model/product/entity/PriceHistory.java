package com.es.phoneshop.model.product.entity;

import java.math.BigDecimal;
import java.util.Currency;

public class PriceHistory {

    private String date;

    private BigDecimal price;

    private Currency currency;

    public PriceHistory() {
    }


    public PriceHistory(String date, BigDecimal price, Currency currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
