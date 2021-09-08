package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts(List<String> searchTextList, SortField sortField, SortOrder sortOrder);

    void saveProduct(Product product);

    void deleteProduct(Long id);
}
