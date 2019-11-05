package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum SortOptions implements Comparator<Product> {
    DESCRIPTION_ASC(Comparator.comparing(Product::getDescription), "description_asc"),
    DESCRIPTION_DESC(Comparator.comparing(Product::getDescription).reversed(), "description_desc"),
    PRICE_ASC(Comparator.comparing(Product::getPrice), "price_asc"),
    PRICE_DESC(Comparator.comparing(Product::getPrice).reversed(), "price_desc");

    private Comparator<Product> comparator;
    private String sortOptions;

    SortOptions(Comparator<Product> comparator, String sortOptions) {
        this.comparator = comparator;
        this.sortOptions = sortOptions;
    }

    public static Comparator<Product> getComparator(String options) {
        for(SortOptions sortOptions : values()) {
            if(sortOptions.sortOptions.equals(options)) {
                return sortOptions.comparator;
            }
        }
        return null;
    }

    @Override
    public int compare(Product product1, Product product2) {
        return comparator.compare(product1, product2);
    }
}
