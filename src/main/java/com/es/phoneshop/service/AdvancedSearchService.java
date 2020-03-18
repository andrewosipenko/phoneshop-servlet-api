package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface AdvancedSearchService {
    List<Product> advancedSearch(String query, String stringMinPrice, String stringMaxPrice,
                                 String stringMinStock, String stringMaxStock);
}
