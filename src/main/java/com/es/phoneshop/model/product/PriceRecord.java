package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class PriceRecord implements Serializable {
    private BigDecimal price;
    private Currency currency;
    private Date date;

    public PriceRecord(BigDecimal price, Currency currency, Date date) {
        this.price = price;
        this.currency = currency;
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

    public String getStringDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        String formattedDate = dateFormat.format(this.date);
        return formattedDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
