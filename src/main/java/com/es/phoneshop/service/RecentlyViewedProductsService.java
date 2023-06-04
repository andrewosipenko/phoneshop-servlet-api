package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface RecentlyViewedProductsService {
    void addRecentlyViewedProduct(HttpSession session, Product product);
    List<Product> getRecentlyViewedProducts(HttpSession session);
}
