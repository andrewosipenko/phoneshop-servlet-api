package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items;

    private Product lastViewedProduct;
    private final List<Product> recentlyViewedProducts;

    public Cart() {
        this.items = new ArrayList<>();
        this.recentlyViewedProducts = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Product getLastViewedProduct() {
        return lastViewedProduct;
    }

    public void setLastViewedProduct(Product lastViewedProduct) {
        this.lastViewedProduct = lastViewedProduct;
    }

    public List<Product> getRecentlyViewedProducts() {
        return recentlyViewedProducts;
    }

    @Override
    public String toString() {
        return "[" + items + "]";
    }
}