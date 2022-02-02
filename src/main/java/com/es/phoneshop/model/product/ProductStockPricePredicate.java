package com.es.phoneshop.model.product;

import java.util.function.Predicate;

public class ProductStockPricePredicate implements Predicate<Product> {
    @Override
    public boolean test(Product o) {
        return o.getPrice() != null && o.getStock() > 0;
    }
}
