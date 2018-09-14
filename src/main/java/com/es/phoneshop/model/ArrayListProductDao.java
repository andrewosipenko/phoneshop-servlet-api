package com.es.phoneshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao{
    private static volatile ArrayListProductDao instance;
    private List<Product> productList;
    private static long counter = 0;

    private ArrayListProductDao() {
        productList = new ArrayList<>();
    }

    public static ArrayListProductDao getInstance() {
        ArrayListProductDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ArrayListProductDao();
                }
            }
        }
        return localInstance;
    }

    @Override
    public synchronized void generateID(Product product) {
        product.setId(counter);
        counter++;
    }

    @Override
    public synchronized List<Product> findProducts() {
        return productList
                .stream()
                .filter((p) -> p.getPrice() != null && p.getPrice().signum() == 1 && p.getStock() != null && p.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized Product getProduct(Long id) throws NoSuchElementException{
        return productList
                .stream()
                .filter((product) -> product.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public synchronized void save(Product product) {
        if (productList.stream()
                .noneMatch((p) -> p.equals(product)))
            productList.add(product);
    }

    @Override
    public synchronized void remove(Long id) {
        productList.remove(getProduct(id));
    }
}

