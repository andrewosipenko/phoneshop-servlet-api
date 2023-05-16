package com.es.phoneshop.dao;

import com.es.phoneshop.enums.SortingField;
import com.es.phoneshop.enums.SortingType;
import com.es.phoneshop.model.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts(String description, SortingField sortingField, SortingType sortingType);

    void save(Product product);

    void delete(Long id);

    int countFoundWords(String description, String productDescription);
}
