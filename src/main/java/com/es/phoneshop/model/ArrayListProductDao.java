package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance = new ArrayListProductDao();

    private List<Product> products;

    private ArrayListProductDao() {
        products = new ArrayList<Product>();
    }

    public static ArrayListProductDao getInstance() {
        return instance;
    }


    public synchronized Product getProduct(Long id) {
        for (Product prod : products) {
            if (prod.getId().equals(id))
                return prod;
        }
        return null;
    }

    public synchronized List<Product> findProducts() {
        ArrayList<Product> result = new ArrayList<Product>();
        return products.parallelStream()
                .filter(s -> (s.getPrice().doubleValue() > 0 && s.getStock() != 0))
                .collect(Collectors.toList());
    }

    public synchronized void save(Product product) {
        products.add(product);
    }

    public synchronized void delete(Long id) {
        products.parallelStream()
                .filter(s -> s.getId().equals(id)).limit(1).forEach(s -> products.remove(s));
    }


}
