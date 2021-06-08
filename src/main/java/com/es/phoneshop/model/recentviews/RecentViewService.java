package com.es.phoneshop.model.recentviews;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface RecentViewService {
    RecentView getRecentView(HttpServletRequest request);

    void add(RecentView recentView, Product product);
}
