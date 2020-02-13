package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private ArrayList<Product> products = new ArrayList<>();

    public ArrayListProductDao() {
    }

    @Override
    public synchronized Product getProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }

        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products.stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getId() == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }

        boolean noneMatchEqualId = products.stream()
                .noneMatch(product1 -> product1.getId().equals(product.getId()));

        if (noneMatchEqualId) {
            products.add(product);
        } else {
            throw new IllegalArgumentException("Product with such id is already exist");
        }
    }

    @Override
    public synchronized void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }

        boolean isDeleted = products.removeIf(product -> product.getId().equals(id));
        if (!isDeleted) {
            throw new NoSuchElementException("Product with such id doesn't exist");
        }
    }
}
