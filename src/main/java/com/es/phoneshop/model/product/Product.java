package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Product {
    private Long id;
    private String code;
    private String description;
    private BigDecimal price;
    private Currency currency;
    private int stock;
    private String imageUrl;
    private int ammountOfViews;

    public Product() {
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
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

   public void incrementAmmountOfViews(){
       ammountOfViews++;
  }

    public int getAmmountOfViews(){
       return ammountOfViews;
   }

    @Override
    public boolean equals(Object object){
        if (this == object)
            return true;
        if (object == null || this.getClass() != object.getClass())
            return false;
        Product product = (Product) object;
        return this.getStock() == product.getStock() &&
               this.getDescription().equals(product.getDescription()) &&
                this.getPrice().equals(product.getPrice()) &&
                this.getId().equals(product.getId())  &&
                this.getCode().equals(product.getCode()) &&
                this.getCurrency().equals(product.getCurrency()) &&
                this.getImageUrl().equals(product.getImageUrl());

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.code, this.stock, this.id, this.description);
    }
}