package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class PriceHistory {
    private LocalDate date;
    private BigDecimal price;
    private Currency currency;

    public PriceHistory() {
    }

    public PriceHistory(LocalDate date, BigDecimal price, Currency currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
