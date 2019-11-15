package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public class Product implements Comparable<Product>, Serializable {
    private Long id;
    private String code;
    private String description;
    /** null means there is no price because the product is outdated or new */
    private BigDecimal price;
    /** can be null if the price is null */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private ArrayList<PriceHistory> priceHistoryArrayList;

    public Product() {
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock,
                   String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistoryArrayList=new ArrayList<>();
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock,
                   String imageUrl, ArrayList<PriceHistory> priceHistories1) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistoryArrayList=new ArrayList<>();
        this.priceHistoryArrayList=priceHistories1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<PriceHistory> getPriceHistoryArrayList(){return priceHistoryArrayList;}

    public void setPriceHistories(ArrayList<PriceHistory> priceHistoryArrayList){
        priceHistoryArrayList=priceHistoryArrayList;
    }

    public void addPriceHistory(PriceHistory priceHistory){ priceHistoryArrayList.add(priceHistory);}

    @Override
    public boolean equals(Object object){
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Product product=(Product)object;
        return id.equals(product.id) && (code==product.code || code!=null && code.equals(product.getCode())) ;

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result * id+ ((code == null) ? 0 : code.hashCode()));
        return result;
    }

    @Override
    public int compareTo(Product o) {
        return this.id.compareTo(o.id);
    }

    public void setPriceHistoryArrayList(ArrayList<PriceHistory> priceHistoryArrayList) {
        this.priceHistoryArrayList = priceHistoryArrayList;
    }
}