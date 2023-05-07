package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Date;

public class PriceHistory {

    private BigDecimal price;
    private Date startDate;

    public PriceHistory(BigDecimal price, Date startDate) {
        this.price = price;
        this.startDate = startDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
