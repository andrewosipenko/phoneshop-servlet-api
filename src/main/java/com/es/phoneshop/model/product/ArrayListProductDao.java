package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao productDao;

    public static synchronized ArrayListProductDao getInstance() {
        if(productDao == null) {
            productDao = new ArrayListProductDao();
        }
        return (ArrayListProductDao) productDao;
    }

    private List<Product> productList;

    private ArrayListProductDao() {
        productList = new ArrayList<>();
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .findAny().orElseThrow(() -> new ProductNotFoundException("Product with id:" + id + "not found"));
    }

    @Override
    public synchronized List<Product> findProducts() {
        return productList.stream().filter(product -> (product.getStock() > 0) && (product.getPrices() != null))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if(!productList.isEmpty() && findProductsWithEqualsId(product)) {
            throw new IllegalArgumentException("Product with duplication");
        }
        else {
            productList.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        productList.remove(getProduct(id));
    }

    private boolean findProductsWithEqualsId(Product product) {
        return productList.stream()
                .anyMatch(product1 -> product1.getId()
                        .equals(product.getId()));
    }

    public List<Product> getProductList() {
        return productList;
    }
}
