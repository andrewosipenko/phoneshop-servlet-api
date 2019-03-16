package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    synchronized public List<Product> findProducts() {
        return products.stream().
                filter(product -> null != product.getPrice()).
                filter(product -> product.getStock() > 0).
                collect(Collectors.toList());
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
