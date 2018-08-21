package com.es.phoneshop.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static volatile ProductDao instance;
    private List<Product> products;


    private ArrayListProductDao(){
        products = new ArrayList<>();

        save(new Product(1L,"A1B", "desc1",new BigDecimal("123.3"),
                Currency.getInstance(Locale.UK),0));

        save(new Product(2L,"A2B", "desc2",new BigDecimal("101.1"),
                Currency.getInstance(Locale.CANADA),123));

        save(new Product(3L,"A3B", "desc3",new BigDecimal("12323.3"),
                Currency.getInstance(Locale.KOREA),123123));

        save(new Product(4L,"A4B", "desc4",new BigDecimal("112323.3"),
                Currency.getInstance(Locale.JAPAN),1321223));
    }

    public synchronized List<Product> findProducts() {
        return products.stream()
                .filter( (product) -> ( product.getStock() != null && product.getStock() > 0
                        && product.getPrice() != null && product.getPrice().compareTo(BigDecimal.ONE) == 1) )
                .collect(Collectors.toList());
    }

    public synchronized Product getProduct(Long id) {
        for(Product temp : products) {
            if(temp.getId().equals(id)) {
                return temp;
            }
        }
        return null;
    }

    public synchronized void save(Product product) {
        if(products.indexOf(product) == -1) {
            products.add(product);
        }else {
            products.set(products.indexOf(product), product);
        }
    }

    public synchronized boolean remove(Long id) {
        return products.remove(getProduct(id));
    }

    public static synchronized ProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

}
