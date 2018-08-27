package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance = new ArrayListProductDao();
    private List<Product> products;
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    private ArrayListProductDao(){
        products = new ArrayList<>();
    }



    public static ArrayListProductDao getInstance(){
        return instance;
    }

    @Override
    public Product getProduct(Long id) {
        lock.readLock().lock();
        try {
            return products.stream()
                    .filter(product -> product.getId().equals(id))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("No product with such id"));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts() {
        lock.readLock().lock();
        try {
            return products.stream()
                    .filter(p -> p.getPrice().compareTo(BigDecimal.ZERO) != 0 && p.getStock() > 0)
                    .collect(Collectors.toList());
        }
        finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void save(Product product) {
        lock.writeLock().lock();
        products.remove(product);
        products.add(product);
        lock.writeLock().unlock();
    }

    @Override
    public void remove(Long id) {
        lock.writeLock().lock();
        products.remove(getProduct(id));
        lock.writeLock().unlock();
    }
}
