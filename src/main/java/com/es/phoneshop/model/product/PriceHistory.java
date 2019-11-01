package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class PriceHistory {
    private Date date;
    private BigDecimal price;
    private Currency currency;

    public PriceHistory(){
    }

    public PriceHistory(Date date1, BigDecimal price1, Currency currency1){
        date=date1;
        price=price1;
        currency=currency1;
    }

    public BigDecimal getPrice(){return price;}

    public Date getDate(){return date;}

    public Currency getCurrency(){return currency;}

    public void setDate(Date date1){date=date1; }

    public void setPrice(BigDecimal price1){price=price1; }

    public void setCurrency(Currency currency1){currency=currency1;}

    public String getStringDate(){
        SimpleDateFormat format=new SimpleDateFormat("dd MMMM yyyy");
        return format.format(date);
    }
}
