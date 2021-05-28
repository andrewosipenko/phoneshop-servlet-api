package com.es.phoneshop.domain.product.persistence;

import com.es.phoneshop.domain.product.model.Product;
import com.es.phoneshop.domain.product.model.ProductPrice;
import com.es.phoneshop.domain.product.model.ProductRequest;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getById(Long id);

    List<Product> getAllByRequest(ProductRequest productRequest);

    List<Product> getAll();

    Long save(Product product);

    void delete(Long id);

    List<ProductPrice> getPricesHistoryByProductId(Long id);
}
