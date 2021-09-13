package com.es.phoneshop.model.product;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;

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
            return result.stream()
                    .filter(product -> product.getId().equals(id))
                    .findAny()
                    .orElseThrow(() ->
                            new ProductNotFindException(id == -1L ? "There is no product with this id" :
                                    ("There is no product with " + id + " id")));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(List<String> searchTextList, SortField sortField, SortOrder sortOrder) {
        lock.readLock().lock();
        try {
            List<Product> products = result.stream()
                    .filter(product -> product.getPrice() != null)
                    .filter(this::isInStock)
                    .collect(toList());
            if ((searchTextList != null && !searchTextList.isEmpty()) &&
                    !(searchTextList.size() == 1 && searchTextList.get(0).equals(""))) {
                products = products.stream()
                        .filter(product -> shouldDisplayProduct(product, searchTextList))
                        .sorted(comparingDouble(p -> -calculateEqualsPercent(p, searchTextList)))
                        .collect(toList());
            }
            return sortProducts(products, sortField, sortOrder);
        } finally {
            lock.readLock().unlock();
        }
    }

    private List<Product> sortProducts(List<Product> products, SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator;
        if (sortField == SortField.PRICE) {
            comparator = Comparator.comparing(Product::getPrice);
        } else if (sortField == SortField.DESCRIPTION) {
            comparator = Comparator.comparing(Product::getDescription);
        } else {
            return products;
        }
        return endSortProduct(products, comparator, sortOrder);
    }

    private List<Product> endSortProduct(List<Product> products, Comparator<Product> comparator, SortOrder sortOrder) {
        products = products.stream()
                .sorted(comparator)
                .collect(toList());
        return (sortOrder == SortOrder.ASCENDING) ? products : products.stream()
                .sorted(comparator.reversed())
                .collect(toList());
    }

    private boolean isInStock(Product product) {
        return product.getStock() > 0;
    }

    private boolean shouldDisplayProduct(Product product, List<String> searchTextList) {
        List<String> searchTextLowerCase = searchTextList.stream()
                .map(String::toLowerCase)
                .collect(toList());
        List<String> descriptionList = Arrays.asList(product.getDescription()
                .toLowerCase(Locale.ROOT).split("\\s"));
        searchTextLowerCase.retainAll(descriptionList);
        return searchTextList.isEmpty() || (searchTextLowerCase.size() != 0);
    }

    private double calculateEqualsPercent(Product product, List<String> searchText) {
        List<String> searchTextLowerCase = searchText.stream()
                .map(String::toLowerCase)
                .collect(toList());
        List<String> descriptionList = Arrays.asList(product.getDescription()
                .toLowerCase(Locale.ROOT).split("\\s"));
        searchTextLowerCase.retainAll(descriptionList);
        int coincidingWords = searchTextLowerCase.size();
        return (double) coincidingWords / descriptionList.size();
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
            } catch (ProductNotFindException exception) {
                product.setId(maxId++);
                result.add(product);
            }
        }
    }

    @Override
    public synchronized void deleteProduct(Long id) throws ProductNotFindException {
        if (result.stream().anyMatch(getProduct(id)::equals)) {
            result.remove(getProduct(id));
        } else {
            throw new ProductNotFindException("There is no product with " + id + " id");
        }
    }
}
