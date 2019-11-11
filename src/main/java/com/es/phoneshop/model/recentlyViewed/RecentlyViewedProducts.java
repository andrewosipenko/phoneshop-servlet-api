package com.es.phoneshop.model.recentlyViewed;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class RecentlyViewedProducts {
    private List<Product> viewedProducts;

    public RecentlyViewedProducts() {
        viewedProducts = new ArrayList<>();
    }

    public RecentlyViewedProducts(List<Product> viewedProducts) {
        this.viewedProducts = viewedProducts;
    }

    public List<Product> getViewedProducts() {
        return viewedProducts;
    }

    public void setViewedProducts(List<Product> viewedProducts) {
        this.viewedProducts = viewedProducts;
    }

    public void addViewedProduct(Product product) {
        viewedProducts.add(product);
    }

    public void deleteViewedProduct(int index) {
        viewedProducts.remove(index);
    }
}
