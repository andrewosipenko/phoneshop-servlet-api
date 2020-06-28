package com.es.phoneshop.services;

import com.es.phoneshop.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RecentlyViewedService {
    public void add(Long productId, HttpServletRequest request);

    public List<Product> getViewedProducts(HttpServletRequest request);
}
