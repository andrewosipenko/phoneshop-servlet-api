package com.es.phoneshop.model.product;

import org.w3c.dom.ls.LSInput;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> result;
    private long maxId;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static volatile ArrayListProductDao instance;

    private ArrayListProductDao() {
        result = new ArrayList<>();
    }

    public static ArrayListProductDao getInstance() {
        ArrayListProductDao result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ArrayListProductDao.class) {
            if (instance == null) {
                instance = new ArrayListProductDao();
            }
            return instance;
        }
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFindException {
        lock.readLock().lock();
        try {
            return result.stream().
                    filter(product -> product.getId().equals(id)).
                    findAny().
                    orElseThrow(() -> new ProductNotFindException("There is no product with " + id + " id"));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(List<String> searchTextList, SortField sortField, SortOrder sortOrder) {
        lock.readLock().lock();
        try {
            List<Product> products = result.stream().
                    filter(product -> product.getPrice() != null).
                    filter(product -> product.getStock() > 0).
                    collect(Collectors.toList());
            products = getSearchProduct(products, searchTextList);
            return sortProducts(products, sortField, sortOrder);
        } finally {
            lock.readLock().unlock();
        }
    }

    private List<Product> getSearchProduct(List<Product> products, List<String> searchTextList) {
        return products.stream().
                filter(product ->
                        searchTextList == null ||
                                searchTextList.isEmpty() ||
                                searchTextList.stream().anyMatch(searchText ->
                                        product.getDescription().toLowerCase().contains(searchText.toLowerCase()))).
                collect(Collectors.toList());
    }

    private List<Product> sortProducts(List<Product> products, SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator;
        if (sortField == SortField.price) {
            comparator = Comparator.comparing(Product::getPrice);
        } else if (sortField == SortField.description) {
            comparator = Comparator.comparing(Product::getDescription);
        } else {
            return products;
        }
        products = products.stream().
                sorted(comparator).
                collect(Collectors.toList());
        return (sortOrder == SortOrder.asc) ?
                products :
                products.stream().sorted(comparator.reversed()).collect(Collectors.toList());
    }

    @Override
    public synchronized void saveProduct(Product product) throws ProductNotFindException {
        if (product.getId() == null) {
            product.setId(maxId++);
            result.add(product);
        } else {
            try {
                getProduct(product.getId());
                long productId = product.getId();
                deleteProduct(productId);
                product.setId(productId);
                result.add(product);
            } catch (NoSuchElementException exception) {
                product.setId(maxId++);
                result.add(product);
            }
        }
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFindException {
        lock.readLock().lock();
        if (result.stream().anyMatch(getProduct(id)::equals)) {
            lock.readLock().unlock();
            lock.writeLock().lock();
            result.remove(getProduct(id));
            lock.writeLock().unlock();
        } else {
            throw new NoSuchElementException();
        }
    }
}
