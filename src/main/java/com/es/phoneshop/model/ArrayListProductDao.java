package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static volatile ArrayListProductDao instance;
    private ArrayList<Product> products;

    private ArrayListProductDao(){
        products = new ArrayList<>();
        Product product = new Product();
        product.setId(Integer.toUnsignedLong(1));
        product.setCode("a1");
        product.setDescription("descr1");
        product.setPrice(BigDecimal.valueOf(12));
        product.setCurrency(Currency.getInstance("BYN"));
        product.setStock(10);
        save(product);


        product = new Product();
        product.setId(Integer.toUnsignedLong(2));
        product.setCode("a2");
        product.setDescription("descr2");
        product.setPrice(BigDecimal.valueOf(12));
        product.setCurrency(Currency.getInstance("BYN"));
        product.setStock(12);
        save(product);

        product = new Product();
        product.setId(Integer.toUnsignedLong(3));
        product.setCode("a3");
        product.setDescription("descr3");
        product.setPrice(BigDecimal.valueOf(12));
        product.setCurrency(Currency.getInstance("BYN"));
        product.setStock(0);
        save(product);

        product = new Product();
        product.setId(Integer.toUnsignedLong(4));
        product.setCode("a4");
        product.setDescription("descr4");
        product.setPrice(BigDecimal.valueOf(0));
        product.setCurrency(Currency.getInstance("BYN"));
        product.setStock(12);
        save(product);
    }

    public static ArrayListProductDao getInstance(){
        ArrayListProductDao localInstance = instance;
        if(localInstance == null){
            synchronized (ArrayListProductDao.class){
                localInstance = instance;
                if(localInstance == null){
                    instance = localInstance = new ArrayListProductDao();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Product getProduct(Long id) {
        for(Product product: products){
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }

    @Override
    public List<Product> findProducts() {
        return products.stream().
                filter(p -> p.getPrice().compareTo(BigDecimal.ZERO) != 0 && p.getStock() > 0).
                collect(Collectors.toList());

    }

    @Override
    public void save(Product product) {
        if(getProduct(product.getId()) == null){
            products.add(product);
        }
    }

    @Override
    public void remove(Long id) {
        products.remove(getProduct(id));
    }
}
