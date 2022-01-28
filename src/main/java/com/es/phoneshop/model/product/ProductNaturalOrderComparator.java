package com.es.phoneshop.model.product;

import java.util.Comparator;

public class ProductNaturalOrderComparator implements Comparator<Product> {

    @Override
    public int compare(Product left, Product right) {
        return left.getDescription().compareToIgnoreCase(right.getDescription());
    }
}
