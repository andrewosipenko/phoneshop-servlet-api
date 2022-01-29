package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.SortField;
import com.es.phoneshop.dao.SortOrder;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private final List<Product> products;
    private long currentId;
    private final ReadWriteLock locker;

    public ArrayListProductDao() {
        products = new ArrayList<>();
        locker = new ReentrantReadWriteLock();
        saveSampleProducts();
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        locker.readLock().lock();
        try {
            if (id == null) {
                throw new ProductNotFoundException("Null id");
            }
            return products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny()
                    .orElseThrow(() -> new ProductNotFoundException("No product with id " + id));
        } finally {
            locker.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        locker.readLock().lock();
        try {
            List<Product> result = products;

            if (query != null && !query.isEmpty()) {
                result = products.stream()
                        .filter(product -> countMatchingWords(product, query) != 0)
                        .sorted(Comparator.comparingInt(product -> countMatchingWords((Product) product, query))
                                .reversed())
                        .collect(Collectors.toList());
            }

            if (sortField != null) {
                result = result.stream()
                        .sorted(getComparator(sortField, sortOrder))
                        .collect(Collectors.toList());
            }

            return result.stream()
                    .filter(this::productHasNonNullPrice)
                    .filter(this::productIsInStock)
                    .collect(Collectors.toList());
        } finally {
            locker.readLock().unlock();
        }
    }

    private Comparator<Product> getComparator(SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator = (product1, product2) -> {
            if (SortField.description == sortField) {
                return product1.getDescription().compareTo(product2.getDescription());
            } else {
                return product1.getPrice().compareTo(product2.getPrice());
            }
        };
        if (SortOrder.desc == sortOrder) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    private int countMatchingWords(Product product, String query) {
        String[] queryTerms = query.toLowerCase().split("\\s");
        String[] productDescriptionTerms = product.getDescription().toLowerCase().split("\\s");
        return (int) Arrays.stream(productDescriptionTerms)
                .filter(Arrays.asList(queryTerms)::contains)
                .count();
    }

    private boolean productHasNonNullPrice(Product product) {
        return product.getPrice() != null;
    }

    private boolean productIsInStock(Product product) {
        return product.getStock() > 0;
    }

    @Override
    public void save(Product product) throws ProductNotFoundException {
        locker.writeLock().lock();
        try {
            if (product.getId() == null) {
                product.setId(++currentId);
                products.add(product);
            } else {
                int index = products.indexOf(getProduct(product.getId()));
                products.set(index, product);
            }
        } finally {
            locker.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) {
        locker.writeLock().lock();
        try {
            products.removeIf(product -> id.equals(product.getId()));
        } finally {
            locker.writeLock().unlock();
        }
    }

    private void saveSampleProducts() {
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
}
