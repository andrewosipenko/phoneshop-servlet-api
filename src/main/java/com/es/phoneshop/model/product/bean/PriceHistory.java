package com.es.phoneshop.model.product.bean;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceHistory {
    private LocalDate dateStart;
    private BigDecimal price;

    public PriceHistory(LocalDate dateStart, BigDecimal price) {
        this.dateStart = dateStart;
        this.price = price;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
