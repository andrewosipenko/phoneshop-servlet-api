package com.es.phoneshop.model.product.bean;

import java.util.ArrayList;
import java.util.List;

public class RecentViewCart {
    private List<Product> products;

    public RecentViewCart() {
        this.products = new ArrayList<>();
    }

    public List<Product> getItems() {
        return products;
    }
}
