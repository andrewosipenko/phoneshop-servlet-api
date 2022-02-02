package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.Comparator;

public class ProductSearchComparator implements Comparator<Product> {
    private final ArrayList<String> queryWords;

    public ProductSearchComparator(ArrayList<String> queryWords) {
        this.queryWords = queryWords;
    }

    @Override
    public int compare(Product a, Product b) {
        int a_counter = 0;
        int b_counter = 0;
        for (String i : queryWords) {
            if (a.getDescription().toLowerCase().contains(i.toLowerCase())) {
                a_counter++;
            }
            if (b.getDescription().toLowerCase().contains(i.toLowerCase())) {
                b_counter++;
            }
        }
        return b_counter - a_counter;
    }
}

