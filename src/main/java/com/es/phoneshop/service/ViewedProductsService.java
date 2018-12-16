package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ViewedProductsList;

public interface ViewedProductsService {
    void addToViewed(Product product, ViewedProductsList productList);
}
