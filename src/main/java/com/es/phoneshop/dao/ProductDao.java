package com.es.phoneshop.dao;

import com.es.phoneshop.enums.SearchType;
import com.es.phoneshop.enums.SortingField;
import com.es.phoneshop.enums.SortingType;
import com.es.phoneshop.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts(String description, SortingField sortingField, SortingType sortingType);

    List<Product> advancedFindProducts(String description, BigDecimal minPrice, BigDecimal maxPrice, SearchType searchType);

    void save(Product product);

    void delete(Long id);
}
