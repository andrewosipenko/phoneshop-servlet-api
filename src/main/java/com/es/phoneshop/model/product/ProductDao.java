package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException;

    Product getProduct(String code) throws ProductNotFoundException;

    List<Product> findProducts();

    List<Product> findProductsByDescription(String description);

    void save(Product product);

    void delete(Long id) throws ProductNotFoundException;
}
