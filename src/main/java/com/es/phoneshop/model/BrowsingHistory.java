package com.es.phoneshop.model;

import java.util.LinkedList;

public class BrowsingHistory {
    private LinkedList<Product> products;

    public BrowsingHistory() {
        this.products = new LinkedList<>();
    }

    public LinkedList<Product> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Product> products) {
        this.products = products;
    }
}
