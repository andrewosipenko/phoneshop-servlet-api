package com.es.phoneshop.model.recentlyViewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface ViewedProductsService {
    RecentlyViewedProducts getViewedProducts(HttpServletRequest request);

    void add(RecentlyViewedProducts viewedProducts, Product product);
}
