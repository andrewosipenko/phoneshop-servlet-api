package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Price {
    private LocalDate date;
    private BigDecimal cost;

    public Price(LocalDate date, BigDecimal cost) {
        this.date = date;
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
