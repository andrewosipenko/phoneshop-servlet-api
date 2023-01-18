package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.entity.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface ProductService {
    Long parseProductIdFromRequestWithHistory(HttpServletRequest request) throws NumberFormatException;

    Long parseProductIdFromRequestWithoutHistory(HttpServletRequest request) throws NumberFormatException;

    void includeProductInRecentProducts(Product product, HttpServletRequest request);

    void setRecentProductsInSession(HttpServletRequest request, Set<Product> products);
}
