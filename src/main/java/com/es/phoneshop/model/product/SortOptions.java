package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum SortOptions implements Comparator<Product> {
    DESCRIPTION_ASC(Comparator.comparing(Product::getDescription)),
    DESCRIPTION_DESC(Comparator.comparing(Product::getDescription).reversed()),
    PRICE_ASC(Comparator.comparing(Product::getPrice)),
    PRICE_DESC(Comparator.comparing(Product::getPrice).reversed());

    private Comparator<Product> comparator;
    private String sortOptions;

    SortOptions(Comparator<Product> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(Product product1, Product product2) {
        return comparator.compare(product1, product2);
    }
}
