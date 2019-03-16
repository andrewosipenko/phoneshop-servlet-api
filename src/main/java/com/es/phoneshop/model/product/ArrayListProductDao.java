package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products = new ArrayList<>();

    @Override
    public Product getProduct(Long id) throws NoSuchElementException {
        return products.stream().
                filter(product -> product.getId().equals(id)).
                findAny().
                orElseThrow(() -> new NoSuchElementException(id.toString()));
    }

    @Override
    public List<Product> findProducts() {
        return products;
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public void delete(Long id) throws NoSuchElementException {
        products.remove(
                products.stream().
                        filter(product -> product.getId().equals(id)).
                        findAny().
                        orElseThrow(() -> new NoSuchElementException(id.toString()))
        );
    }
}
