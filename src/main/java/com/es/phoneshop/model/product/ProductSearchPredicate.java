package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ProductSearchPredicate implements Predicate<Product> {
    private final ArrayList<String> queryWords;

    ProductSearchPredicate(ArrayList<String> queryWords) {
        this.queryWords = queryWords;
    }

    @Override
    public boolean test(Product product) {
        if (queryWords.size() == 0) {
            return true;
        }
        return queryWords.stream()
                .anyMatch(str -> product.getDescription().toLowerCase().contains(str.toLowerCase()));
    }
}
