package com.es.phoneshop.model;

import java.io.Serializable;
import java.util.LinkedList;

public class BrowsingHistory implements Serializable {
    private LinkedList<Product> products;
    private static final long serialVersionUID = 1111L;

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
