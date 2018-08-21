package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static volatile ArrayListProductDao instance;
    private List<Product> products;

    public static ArrayListProductDao getInstance() {
        if (instance == null){
            synchronized (ArrayListProductDao.class) {
                if (instance == null){
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    private ArrayListProductDao() {
        products = new ArrayList<>();

        save(new Product(1L, "a004", "first product",
                null, Currency.getInstance("USD"), 0));

        save(new Product(2L, "a002", "second product",
                new BigDecimal("6.95"), Currency.getInstance("USD"), 100));

        save(new Product(3L, "a003", "third product",
                new BigDecimal("4.50"), Currency.getInstance("USD"), 7));

        save(new Product(4L, "a004", "fourth product",
                new BigDecimal("9.99"), Currency.getInstance("USD"), 54));
    }

    public synchronized void save(Product product) {
        products.add(product);
    }

    public synchronized Product getProduct(long id) {
        Product product = products.stream().filter((p) -> p.getId().equals(id))
                .findFirst().orElse(null);
        if (product != null) {
            return product;
        } else {
            throw new RuntimeException("Not implemented.");
        }
    }

    public synchronized List<Product> findProducts() {
        return products.stream().filter(p -> p.getPrice() != null && p.getStock() > 0)
                .collect(Collectors.toList());
    }

    public synchronized void remove(long id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(i);
                break;
            }
        }
    }
}
