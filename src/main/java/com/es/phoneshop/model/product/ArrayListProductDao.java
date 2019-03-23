package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private final static ArrayListProductDao instance = new ArrayListProductDao();
    private List<Product> products;

    private ArrayListProductDao() {
        products = new ArrayList<>();
    }


    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        if (id == null) throw new NullPointerException("Id cant be null!");
        if (id < 0) throw new IllegalArgumentException("Id cant be negative!");
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public Product getProductByCode(String code) throws ProductNotFoundException {
        return products.stream()
                .filter(product -> product.getCode().toLowerCase().equals(code))
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<Product> findProducts() {
        return products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findProductsByDescription(String description) {
        String formattedDescription = description.trim().replaceAll("\\s{2,}", " ").toLowerCase();
        return products.stream()
                .filter(product -> product.getDescription().toLowerCase().contains(formattedDescription))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product == null) throw new NullPointerException("Product cant be null!");
        if (products
                .stream()
                .anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new IllegalArgumentException("Product with this id already exist!");
        }
        products.add(product);
    }

    @Override
    public synchronized void delete(Long id) throws ProductNotFoundException {
        products.remove(getProduct(id));
    }

    public static ArrayListProductDao getInstance() {
        return instance;
    }
}
