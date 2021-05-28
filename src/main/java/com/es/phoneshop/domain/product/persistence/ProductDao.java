package com.es.phoneshop.domain.product.persistence;

import com.es.phoneshop.domain.product.model.Product;
import com.es.phoneshop.domain.product.model.ProductRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getById(@NotNull Long id);

    List<Product> getAll(@NotNull ProductRequest productRequest);

    List<Product> getAll();

    Long save(@NotNull Product product);

    void delete(@NotNull Long id);
}
