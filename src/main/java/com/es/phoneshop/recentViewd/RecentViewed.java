package com.es.phoneshop.recentViewd;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface RecentViewed {
    RecentViewedList getRecentViewedList(HttpServletRequest request);

    void addToRecentViewed(HttpServletRequest request, Product product);
}
