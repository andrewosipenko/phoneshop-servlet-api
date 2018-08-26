package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static volatile ProductDao instance;
    private List<Product> products;


    private ArrayListProductDao(){
        products = new ArrayList<>();
    }

    public synchronized List<Product> findProducts() {
        return products.stream()
                .filter( (product) -> (product.getStock() != null && product.getStock() > 0
                        && product.getPrice() != null && product.getPrice().compareTo(BigDecimal.ZERO) == 1) )
                .collect(Collectors.toList());
    }

    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter((p) -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no products with such id"));
    }

    public synchronized void save(Product product) {
        if(products.indexOf(product) == -1) {
            products.add(product);
        }else {
            products.set(products.indexOf(product), product);
        }
    }

    public synchronized boolean remove(Long id) {
        return products.remove(getProduct(id));
    }

    public static synchronized ProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }
}
