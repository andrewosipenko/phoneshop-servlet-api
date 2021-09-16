package com.es.phoneshop.model.product.recentlyview;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface RecentlyViewService {
    void add(RecentlyViewSection recentlyViewSection, HttpServletRequest request, Product product);

    RecentlyViewSection getRecentlyViewSection(HttpServletRequest request);
}
