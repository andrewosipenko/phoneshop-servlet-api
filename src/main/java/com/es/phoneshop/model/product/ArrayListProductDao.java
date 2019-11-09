package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao productDao;
    private static Lock lock = new ReentrantLock();

    private List<Product> productList;

    public static ArrayListProductDao getInstance() {
        lock.lock();
        try {
            if (productDao == null) {
                productDao = new ArrayListProductDao();
            }
            return (ArrayListProductDao) productDao;
        } finally {
            lock.unlock();
        }
    }

    private ArrayListProductDao() {
        productList = new ArrayList<>();
    }

    @Override
    public Product getProduct(Long id) {
        lock.lock();
        try {
            return productList.stream()
                    .filter(product -> product.getId().equals(id))
                    .findAny().orElseThrow(() -> new ProductNotFoundException("Product with id:" + id + "not found"));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Product> findProducts() {
        lock.lock();
        try {
            return productList.stream().filter(product -> (product.getStock() > 0) && (product.getPrice() != null))
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void save(Product product) {
        lock.lock();
        try {
            if (!productList.isEmpty() && findProductsWithEqualsId(product)) {
                throw new IllegalArgumentException("Product with duplication");
            } else {
                productList.add(product);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void delete(Long id) {
        lock.lock();
        try {
            productList.remove(getProduct(id));
        } finally {
            lock.unlock();
        }
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
