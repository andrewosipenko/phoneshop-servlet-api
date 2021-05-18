package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> findProducts();

    Product getProduct(Long id);

    Product getProduct(String pathInfo);

    void save(Product product);

    void delete(Long id);

    List<Product> advancedSearch(String productCode, BigDecimal minPrice, BigDecimal maxPrice, Integer minStock);

    List<Product> findProducts(String sort, String order, String query);
}
