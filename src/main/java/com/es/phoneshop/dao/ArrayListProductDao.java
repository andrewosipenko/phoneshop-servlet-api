package com.es.phoneshop.dao;

import com.es.phoneshop.model.SortField;
import com.es.phoneshop.model.SortOrder;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exception.ProductNotFoundException;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> productList;
    private long productId;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public ArrayListProductDao() {
        productList = new ArrayList<>();
    }

    protected void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    private int wordsAmount(List<String> wordsList, Product product) {
        String description = product.getDescription();
        int amount = 0;
        for (String word : wordsList) {
            if (description.contains(word)) {
                amount++;
            }
        }
        return amount;
    }

    @Override
    public Product getProduct(Long id) {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            return productList.stream()
                    .filter((product) -> id.equals(product.getId()))
                    .findAny()
                    .orElseThrow(RuntimeException::new);
        }
        finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Product> findProducts(String queryProduct, SortField sortField, SortOrder sortOrder) {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            List<String> wordsList = new ArrayList<>();
            if (queryProduct != null && !queryProduct.isEmpty()) {
                wordsList = Arrays.asList(queryProduct.split(" "));
            }
            List<String> finalWordsList = wordsList;
            Comparator comparator = Comparator.comparing(((product -> {
                        if (sortField == SortField.description) {
                            return (Comparable)((Product) product).getDescription();
                        } else {
                            if (sortField == SortField.price) {
                                return (Comparable) ((Product) product).getPrice();
                            } else {
                                return (Comparable) 1;
                            }
                        }
                    })));

            List<Product> searchResults = productList.stream()
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    .filter(product -> queryProduct == null || queryProduct.isEmpty() || wordsAmount(finalWordsList, product) > 0)
                    .sorted(Comparator.comparing(product -> wordsAmount(finalWordsList, product), Comparator.reverseOrder()))
                    .collect(Collectors.toList());

            if (sortOrder == SortOrder.desc) {
                return (List<Product>) searchResults.stream()
                        .sorted(comparator.reversed())
                        .collect(Collectors.toList());
            }
            else {
                return (List<Product>) searchResults.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
            }
        }
        finally {
            readLock.unlock();
        }
    }

    @Override
    public void save(Product product) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Long id = product.getId();
            if (id != null) {
                productList.remove(getProduct(id));
                productList.add(product);
            }
            else {
                product.setId(++productId);
                productList.add(product);
            }
        }
        finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(Long id) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Product product = getProduct(id);
            productList.remove(product);
        }
        finally {
            writeLock.unlock();
        }
    }

}
