package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.util.Date;

public class PriceUpdatePoint {
    private Date date;
    private BigDecimal price;

    private PriceUpdatePoint() {
    }

    public PriceUpdatePoint(Date date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
