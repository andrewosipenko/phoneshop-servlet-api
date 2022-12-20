package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Product implements Serializable {
    private Long id;
    private String code;
    private String description;
    private BigDecimal price;
    private Currency currency;
    private int stock;
    private String imageUrl;
    private Long queryCoincidence = 1L;
    private List<PriceRecord> priceHistory = new ArrayList<>();

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

    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Long getQueryCoincidence(String[] queries) {
        for (String query : queries) {
            if (description.contains(query)) {
                queryCoincidence++;
            }
        }
        return queryCoincidence;
    }

    public void setPriceHistory(List<PriceRecord> priceRecords) {
        for (PriceRecord record : priceRecords) {
            this.priceHistory.add(record);
        }
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

    public void setQueryCoincidence(Long queryCoincidence) {
        this.queryCoincidence = queryCoincidence;
    }

    public List<PriceRecord> getPriceHistory() {
        return priceHistory;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }

        Product c = (Product) o;
        return Long.compare(id, c.id) == 0
                && code.equals(c.code)
                && description.equals(c.description)
                && price.equals(c.price)
                && currency.equals(c.currency)
                && Integer.compare(stock, c.stock) == 0
                && imageUrl.equals(c.imageUrl);
    }
}