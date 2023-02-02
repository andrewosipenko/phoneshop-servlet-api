package com.es.phoneshop.service;

import com.es.phoneshop.model.recentlyViewedProducts.RecentlyViewedProducts;

import javax.servlet.http.HttpServletRequest;

public interface RecentlyViewedProductsService {
    RecentlyViewedProducts getProducts(HttpServletRequest request);
    void add(Long productId, HttpServletRequest request);
}
