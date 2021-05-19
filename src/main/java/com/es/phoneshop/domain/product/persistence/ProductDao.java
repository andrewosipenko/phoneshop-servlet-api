package com.es.phoneshop.domain.product.persistence;

import com.es.phoneshop.domain.product.model.Product;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ProductDao {
    Product getByIdOrNull(@NotNull Long id);
    List<Product> getAllAvailable();
    void update(@NotNull Product product);
    void create(@NotNull Product product);
    void delete(@NotNull Long id);
}
