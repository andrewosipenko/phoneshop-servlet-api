package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    private String date;
    private BigDecimal price;

    public PriceHistory(){
    }

    public PriceHistory(String date, int price){
        this.date = date;
        this.price = new BigDecimal(price);
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
}
