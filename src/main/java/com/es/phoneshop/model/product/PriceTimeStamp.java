package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PriceTimeStamp implements Serializable {
    private final BigDecimal price;

    private final Date date;

    public PriceTimeStamp(BigDecimal price, Date date) {
        this.price = price;
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
