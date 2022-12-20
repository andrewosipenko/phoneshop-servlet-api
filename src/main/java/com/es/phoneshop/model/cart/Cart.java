package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cart implements Serializable {
    private static final String CURRENCY = "USD";

    private List<CartItem> items;

    private int totalQuantity;
    private BigDecimal totalCost;
    private Currency currency;

    public Cart() {
        this.currency = Currency.getInstance(CURRENCY);
        this.items = new ArrayList<>();
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public boolean containsCartItem(Product product) {
        List<CartItem> list = items.stream()
                .filter(o -> product.equals(o.getProduct()))
                .collect(Collectors.toList());
        return !list.isEmpty();
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Optional<CartItem> getCartItemByName(Product product) {
        return items.stream()
                .filter(o -> product.equals(o.getProduct()))
                .findFirst();
    }

    public Optional<CartItem> getCartItem(Product product, int quantity) {
        CartItem item = new CartItem(product, quantity);
        return items.stream()
                .filter(o -> o.equals(item))
                .findFirst();
    }

    @Override
    public String toString() {
        return "items=[" + items + ']';
    }
}
