package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RecentlyViewedService {
    List<Product> getProducts(HttpServletRequest request);
    void addProduct(HttpServletRequest request, Product product);
}
