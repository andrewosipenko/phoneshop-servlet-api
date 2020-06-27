package com.es.phoneshop.model;

import com.es.phoneshop.enums.ProductOrderBy;
import com.es.phoneshop.enums.ProductSortBy;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products = new ArrayList<>();
    private static ProductDao productDao = new ArrayListProductDao();

    public ArrayListProductDao(List<Product> products) {
        this.products = products;
    }

    public ArrayListProductDao() {
    }

    public static ProductDao getInstance() {
        return productDao;
    }


    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product with this id not found"));
    }

    private List<Product> findProductsByQuery(String query, List<Product> products) {
        String[] words = query.toLowerCase().split(" ");
        return products.stream().filter(product -> Arrays.stream(words)
                .anyMatch(word -> product.getDescription().toLowerCase().contains(word)))
//                sorted my query
                .sorted(Comparator.comparingInt(product -> (int) Arrays.stream(words)
                        .filter(word -> product.getDescription().toLowerCase().contains(word)).count()))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> findProducts(String query, String order, String sort) {
        List<Product> productss = products.stream()
                .filter(product -> product.getStock() > 0)
                .filter(product -> product.getCurrentPrice() != null)
                .collect(Collectors.toList());
        if (query != null) {
            productss = findProductsByQuery(query, productss);
        }
        if (order != null) {
            productss = sortProductsByOrderAndSort(productss, order, sort);
        }
        return productss;
    }

    private List<Product> sortProductsByOrderAndSort(List<Product> productss, String order, String sort) {

        Comparator<Product> productComparator = null;
        switch (ProductOrderBy.valueOf(order.toUpperCase())) {
            case PRICE:
                productComparator = Comparator.comparing(o -> o.getCurrentPrice().getCost());
                break;
            case DESCRIPTION:
                productComparator = Comparator.comparing(Product::getDescription);
                break;
        }

        if (ProductSortBy.valueOf(sort.toUpperCase()) == ProductSortBy.ASC) {
            productComparator = productComparator.reversed();
        }
        return productss.stream().sorted(productComparator).collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        try {
            Product existProduct = getProduct(product.getId());
            update(product, existProduct);
        } catch (NoSuchElementException e) {
            product.setId(Integer.toUnsignedLong(products.size()));
            products.add(product);
        }
    }

    private void update(Product updateProduct, Product oldProduct) {
        oldProduct.setCode(updateProduct.getCode());
        oldProduct.setCurrency(updateProduct.getCurrency());
        oldProduct.setDescription(updateProduct.getDescription());
        oldProduct.setImageUrl(updateProduct.getImageUrl());
        oldProduct.setPrices(updateProduct.getPrices());
        oldProduct.setStock(updateProduct.getStock());
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }

    @Override
    public void saveAll(List<Product> newProducts) {
        System.out.println(newProducts.size());
        newProducts.stream().forEach(this::save);
    }
}
