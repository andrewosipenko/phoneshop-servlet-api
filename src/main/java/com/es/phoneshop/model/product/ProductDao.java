package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFindException;

    List<Product> findProducts(List<String> searchTextList, SortField sortField, SortOrder sortOrder);

    void saveProduct(Product product) throws ProductNotFindException;

    void deleteProduct(Long id) throws ProductNotFindException;
}
