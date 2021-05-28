package com.es.phoneshop.domain.product.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class ProductPrice {
    private Long productId;
    private LocalDateTime from;
    private BigDecimal value;
    private Currency currency;

    public ProductPrice(Long productId, LocalDateTime from, BigDecimal value, Currency currency) {
        this.productId = productId;
        this.from = from;
        this.value = value;
        this.currency = currency;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
