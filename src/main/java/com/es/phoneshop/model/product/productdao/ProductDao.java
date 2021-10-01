package com.es.phoneshop.model.product.productdao;

import com.es.phoneshop.model.product.enums.sort.SortField;
import com.es.phoneshop.model.product.enums.sort.SortOrder;
import com.es.phoneshop.model.product.exceptions.ItemNotFindException;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ItemNotFindException;

    List<Product> findProducts(List<String> searchTextList, SortField sortField, SortOrder sortOrder);

    void saveProduct(Product product) throws ItemNotFindException;

    void deleteProduct(Long id) throws ItemNotFindException;
}