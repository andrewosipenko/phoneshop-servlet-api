package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.enums.SortBy;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductDao {

    Product getProduct(Long id) throws ProductNotFoundException;

    List<Product> findProducts();

    List<Product> findProducts(String query);

    void save(Product product);

    void delete(Long id) throws ProductNotFoundException;

    List<Product> sort(List<Product> products, SortBy field, boolean ascending);
}
