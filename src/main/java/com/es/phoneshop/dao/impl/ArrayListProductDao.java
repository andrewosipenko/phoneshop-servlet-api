package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private long maxId;
    private final List<Product> products;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public ArrayListProductDao() {
        maxId = 0;
        products = new ArrayList<>();
        getSampleProducts();
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        readWriteLock.readLock().lock();
        try {
            return products.stream()
                    .filter(product -> product.getId().equals(id) && product.getPrice() != null && product.getStock() > 0)
                    .findAny()
                    .orElseThrow(() -> new ProductNotFoundException("Product wasn't found"));

        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts() {
        readWriteLock.readLock().lock();
        try {
            return products.stream()
                    .filter(product -> product.getPrice() != null && product.getStock() > 0)
                    .collect(Collectors.toList());
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public void save(Product product) {
        readWriteLock.writeLock().lock();
        try {
            Optional<Product> oldProduct = products.stream().filter(pr -> pr.getId().equals(product.getId())).findAny();
            if (oldProduct.isPresent()) {
                int index = products.indexOf(oldProduct.get());
                products.set(index, product);
            } else {
                product.setId(maxId++);
                products.add(product);
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) {
        readWriteLock.writeLock().lock();
        try {
            products.removeIf(product -> product.getId().equals(id));
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private void getSampleProducts() {
        readWriteLock.writeLock().lock();
        try {
            Currency usd = Currency.getInstance("USD");
            save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "/Samsung/Samsung%20Galaxy%20S.jpg"));
            save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
            save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
            save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "/Apple/Apple%20iPhone.jpg"));
            save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "/Apple/Apple%20iPhone%206.jpg"));
            save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "/HTC/HTC%20EVO%20Shift%204G.jpg"));
            save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "/Sony/Sony%20Ericsson%20C901.jpg"));
            save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "/Sony/Sony%20Xperia%20XZ.jpg"));
            save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "/Nokia/Nokia%203310.jpg"));
            save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "/Palm/Palm%20Pixi.jpg"));
            save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "/Siemens/Siemens%20C56.jpg"));
            save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "/Siemens/Siemens%20C61.jpg"));
            save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "/Siemens/Siemens%20SXG75.jpg"));
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
