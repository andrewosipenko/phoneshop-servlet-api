package com.es.phoneshop.recentViewd;

import com.es.phoneshop.model.product.Product;

import java.util.*;

public class RecentViewedList {
    private final List<Product> items;
    private final int size;

    public RecentViewedList() {
        size = 3;
        this.items = new ArrayList<>(size);
    }

    public List<Product> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "[" + items + "]";
    }
}
