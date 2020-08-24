package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findProducts();

    Product getProduct(Long id);

    Product getProduct(String pathInfo);

    void save(Product product);

    void delete(Long id);

    List<Product> findProducts(String sort, String order, String query);
}
