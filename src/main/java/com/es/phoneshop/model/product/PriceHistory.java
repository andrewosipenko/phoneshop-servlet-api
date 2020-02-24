package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;

public class PriceHistory {
    private String date;
    private BigDecimal price;
    private Currency currency;

    public PriceHistory(String date, BigDecimal price, Currency currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }
}
