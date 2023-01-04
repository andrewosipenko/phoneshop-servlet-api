package com.es.phoneshop.model.product.dao.impl;

import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private long currentMaxId = 1;

    public ArrayListProductDao() {
        products = new ArrayList<>();
        setSampleProducts();
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        if (id == null)
            throw new IllegalArgumentException("Parameter id is null");
        lock.readLock().lock();
        Product product = products.stream().
                filter(currProduct -> id.equals(currProduct.getId())).
                findAny().
                orElseThrow(() -> new ProductNotFoundException(String.format("No such product with given id:%d", id)));
        lock.readLock().unlock();
        return product;
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products.stream().
                filter(Objects::nonNull).
                filter(product -> product.getPrice() != null).
                filter(product -> product.getStock() > 0).
                collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        lock.writeLock().lock();
        if (product.getId() != null) {
            Optional<Product> productToAdd = products.stream().
                    filter(currProduct -> currProduct.getId().equals(product.getId())).
                    findAny();
            if (productToAdd.isEmpty()) {
                addNewProduct(product, products);
            } else
                products.set(products.indexOf(productToAdd.get()), product);
        } else {
            addNewProduct(product, products);
        }
        lock.writeLock().unlock();
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Parameter id is null");
        lock.writeLock().lock();
        if (!products.removeIf(product -> id.equals(product.getId())))
            throw new ProductNotFoundException(String.format("Cannot delete product with given id:%d", id));
        lock.writeLock().unlock();
    }

    private void setSampleProducts() {
        Currency usd = Currency.getInstance("USD");

        save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

    private void addNewProduct(Product product, List<Product> products) {
        product.setId(currentMaxId++);
        products.add(product);
    }
}
