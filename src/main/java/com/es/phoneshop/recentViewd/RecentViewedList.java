package com.es.phoneshop.recentViewd;

import com.es.phoneshop.model.product.Product;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class RecentViewedList {
    private final List<Product> items;

    public RecentViewedList() {
        this.items = new CopyOnWriteArrayList<>();
    }

    public List<Product> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "[" + items + "]";
    }
}
