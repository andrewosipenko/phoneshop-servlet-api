package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class PriceHistory {
    private String statDate;
    private BigDecimal price;
    private Currency currency;

    public PriceHistory () { }

    public PriceHistory(String startDate, BigDecimal price, Currency currency) {
        this.statDate = startDate;
        this.price = price;
        this.currency = currency;
    }

    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate;
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
