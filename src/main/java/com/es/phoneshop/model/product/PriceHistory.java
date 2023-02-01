package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class PriceHistory implements Serializable {
    private static final long serialVersionUID = -2777778953420112400L;

    private LocalDateTime startDate;
    private BigDecimal price;
    private Currency currency;

    public PriceHistory() {
    }

    public PriceHistory(LocalDateTime startDate, BigDecimal price, Currency currency) {
        this.startDate = startDate;
        this.price = price;
        this.currency = currency;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
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
