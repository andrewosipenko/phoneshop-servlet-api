package com.es.phoneshop.model.recentlyViewedProducts;

import javax.servlet.http.HttpServletRequest;

public interface RecentlyViewedProductsService {
    RecentlyViewedProducts getProducts(HttpServletRequest request);

    void add(Long productId, HttpServletRequest request);
}
