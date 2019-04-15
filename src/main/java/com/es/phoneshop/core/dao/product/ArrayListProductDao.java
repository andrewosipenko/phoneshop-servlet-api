package com.es.phoneshop.core.dao.product;

import com.es.phoneshop.core.exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ArrayListProductDao instance;
    private List<Product> products;

    private ArrayListProductDao() {
        products = new ArrayList<>();
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


    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        if (id == null) {
            throw new NullPointerException("Id cant be null!");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id cant be negative!");
        }
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public List<Product> findProducts() {
        return products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findProducts(String query) {
        String[] searchWords = query
                .trim()
                .replaceAll("\\s{2,}", " ")
                .toLowerCase()
                .split(" ");

        return products.stream()
                .filter(product -> product.getDescription() != null)
                .filter(product -> Arrays.stream(searchWords)
                        .anyMatch(product.getDescription().toLowerCase()::contains))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product == null) {
            throw new NullPointerException("Product cant be null!");
        }
        if (products
                .stream()
                .anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new IllegalArgumentException("Product with this id already exist!");
        }
        products.add(product);
    }

    @Override
    public synchronized void delete(Long id) throws ProductNotFoundException {
        products.remove(getProduct(id));
    }

    /**
     * @param field     (if null, return {@param products} without any sorting)
     * @param ascending
     * @return sorted by some field products
     */
    public List<Product> findProducts(String query, SortBy field, boolean ascending) {
        List<Product> products = query == null ? findProducts() : findProducts(query);
        boolean readyToSort = field != null;
        Comparator<Product> productComparator = null;
        if (readyToSort && products.size() > 1) {
            switch (field) {
                case DESCRIPTION:
                    productComparator = Comparator.comparing(Product::getDescription, Comparator.comparing(String::toLowerCase));
                    break;
                case PRICE:
                    productComparator = Comparator.comparing(Product::getPrice);
                    break;
            }

            if (productComparator == null) {
                return products;
            }

            if (!ascending) {
                productComparator = productComparator.reversed();
            }
            products.sort(productComparator);
        }
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
