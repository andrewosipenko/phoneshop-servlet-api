package com.es.phoneshop.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static volatile  ArrayListProductDao instance;
    private ArrayList<Product> products;


    private ArrayListProductDao(){
        products = new ArrayList<Product>();
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }

    public List<Product> findProducts() {
        return products.stream()
                .filter( (product) -> (product.getStock() > 0
                        && product.getPrice().compareTo(BigDecimal.ONE) == 1) )
                .collect(Collectors.toList());
    }

    public Product getProduct(Long id) {
        for(Product temp : products){
            if(temp.getId().equals(id)){
                return temp;
            }
        }
        return null;
    }

    public void save(Product product){
        for(Product temp : products){
            if(temp.getId().equals(product.getId())){
                products.set(products.indexOf(temp), product);
                return;
            }
        }
        products.add(product);
    }

    public boolean remove(Long id) {
        for(Product temp : products){
            if(temp.getId().equals(id)){
                return products.remove(temp);
            }
        }
        return false;
    }

    public static synchronized ArrayListProductDao getInstance() {
        if (instance == null) {
            if (instance == null) {
                instance = new ArrayListProductDao();
            }
        }
        return instance;
    }

}
