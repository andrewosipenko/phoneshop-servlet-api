package com.es.phoneshop.model.recentlyViewedProducts;

import com.es.phoneshop.model.product.Product;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;


public class RecentlyViewedProducts {
    private Deque<Product> products;

    public RecentlyViewedProducts() {
        this.products = new LinkedList<>();
    }

    public Deque<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Recently viewed products:" + products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecentlyViewedProducts that = (RecentlyViewedProducts) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }
}
