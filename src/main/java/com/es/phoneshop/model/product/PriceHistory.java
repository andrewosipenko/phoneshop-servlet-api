package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class PriceHistory implements Serializable {
    private Date date;
    private BigDecimal price;
    private Currency currency;

    public PriceHistory(){
    }

    public PriceHistory(Date date, BigDecimal price, Currency currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public String getStringDate(){
        SimpleDateFormat format=new SimpleDateFormat("dd MMMM yyyy");
        return format.format(date);
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
