package com.es.phoneshop.web;

import com.es.phoneshop.model.Product;

public class ProductIDGenerator {
    private static long counter = 0;

    public static synchronized void generateID(Product product) {
        product.setId(counter);
        counter++;
    }
}
