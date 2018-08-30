package com.es.phoneshop.model;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDAO implements ProductDAO{
    private List<Product> products;
    private static volatile ArrayListProductDAO instance = null;

    private static Long counter = 1L;

    public static synchronized Long generateId(){
        return counter++;
    }

    public static ArrayListProductDAO getInstance(){
        if (instance == null){
            synchronized (ArrayListProductDAO.class){
                if (instance == null)
                    instance = new ArrayListProductDAO();
            }
        }
        return instance;
    }

    private ArrayListProductDAO() {
        products = new ArrayList<>();
    }

    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter((p) -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No products with such id."));
    }

    public List<Product> findProducts() {
        List<Product> productList = products.stream()
                .filter(a -> a.getPrice() != null && a.getStock() > 0)
                .collect(Collectors.toList());
        return productList;
    }

    public void save(Product product) {
        products.add(product);
    }

    public synchronized void remove(Long id) {
        products.remove(getProduct(id));
    }
}
