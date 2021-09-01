package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts();

    void saveProduct(Product product);

    void deleteProduct(Long id);
}
