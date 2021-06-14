package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.sortenum.SortField;
import com.es.phoneshop.model.sortenum.SortOrder;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;
    private long maxId;

    private static ArrayListProductDao instance;
    private ReadWriteLock rwLock;

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
        this.rwLock = new ReentrantReadWriteLock();
    }

    public static ArrayListProductDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    private long countWordsAmount(String searchQuery, Product product) {
        List<String> wordsList = new ArrayList<>();
        if (searchQuery != null && !searchQuery.isEmpty()) {
            wordsList = Arrays.asList(searchQuery.split(" "));
        }
        String description = product.getDescription();
        return wordsList.stream()
                .filter(description::contains)
                .count();
    }

    private Comparator<Product> getComparator(String searchQuery, SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator;
        if (sortField == SortField.DESCRIPTION) {
            comparator = Comparator.comparing(Product::getDescription);
        } else if (sortField == SortField.PRICE) {
            comparator = Comparator.comparing(Product::getPrice);
        } else {
            comparator = Comparator.comparing(product -> countWordsAmount(searchQuery, product), Comparator.reverseOrder());
        }
        if (sortOrder == SortOrder.DESC) {
            comparator = comparator.reversed();
        }
        return comparator;
    }


    @Override
    public Optional<Product> getProduct(@NonNull final Long id) {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            return products.stream()
                    .filter(product -> product.getId().equals(id))
                    .findAny();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Product> findProducts(@NonNull final String searchQuery,
                                      @NonNull final SortField sortField,
                                      @NonNull final SortOrder sortOrder) {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            return products.stream()
                    .filter(this::nonNullPrice)
                    .filter(this::productIsInStock)
                    .filter(product -> StringUtils.isBlank(searchQuery) || countWordsAmount(searchQuery, product) > 0)
                    .sorted(this.getComparator(searchQuery, sortField, sortOrder))
                    .collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    private boolean productIsInStock(@NonNull final Product product) {
        return product.getStock() > 0;
    }

    private boolean nonNullPrice(@NonNull final Product product) {
        return product.getPrice() != null;
    }

    @Override
    public void save(@NonNull final Product product) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            if (product.getId() != null) {
                Optional<Product> optProduct = products.stream()
                        .filter(prod -> prod.getId().equals(product.getId()))
                        .findAny();

                optProduct.ifPresent(products::remove);
            } else {
                product.setId(maxId++);
            }
            products.add(product);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(@NonNull final Long id) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Optional<Product> optProduct = products.stream()
                    .filter(product -> product.getId().equals(id))
                    .findAny();
            optProduct.ifPresent(products::remove);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void changePrice(@NonNull final Long id, @NonNull final BigDecimal price) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            products.stream()
                    .filter(product -> product.getId().equals(id))
                    .forEach(product -> product.setPrice(price));
        } finally {
            writeLock.unlock();
        }
    }

}


