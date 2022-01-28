package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private final List<Product> products;

    private long maxId = 0;

    ReadWriteLock lock = new ReentrantReadWriteLock();

    public ArrayListProductDao() {
        this.products = new ArrayList<>();
        fillProducts();
    }

    private void fillProducts() {
        Currency usd = Currency.getInstance("USD");
        save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "manufacturer/Apple/Apple%20iPhone.jpg"));
        save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "manufacturer/Apple/Apple%20iPhone%206.jpg"));
        save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "manufacturer/Nokia/Nokia%203310.jpg"));
        save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "manufacturer/Palm/Palm%20Pixi.jpg"));
        save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "manufacturer/Siemens/Siemens%20C56.jpg"));
        save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "manufacturer/Siemens/Siemens%20C61.jpg"));
        save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

    @Override
    public Optional<Product> getProduct(Long id) throws NoSuchElementException {
        if (id == null) {
            return Optional.empty();
        }
        synchronized (lock.readLock()) {
            return products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny();
        }

    }

    @Override
    public List<Product> findProducts() {
        synchronized (lock.readLock()) {
            return products
                    .stream()
                    .filter(product -> product.getPrice() != null && product.getStock() > 0)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void save(Product product) {
        synchronized (lock.writeLock()) {
            if (product.getId() != null) {
                products.forEach(product1 -> {
                    if (product1.getId().equals(product.getId())) {
                        products.set(products.indexOf(product1), product);
                    }
                });
            } else {
                product.setId(maxId++);
                products.add(product);
            }
        }
    }

    @Override
    public void delete(Long id) {
        synchronized (lock.writeLock()) {
            products.removeIf(product -> id.equals(product.getId()));
        }
    }
}
