package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items;

    public Cart() {
        this.items = new LinkedHashMap<>();
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    void add(Product p, int quantity) {
        Integer amount = items.get(p);
        items.put(p, (amount == null ? 0 : amount) + quantity);
    }

    int get(Product p) {
        Integer amount = items.get(p);
        return amount == null ? 0 : amount;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart: [");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            stringBuilder.append(entry.getKey().getCode());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
