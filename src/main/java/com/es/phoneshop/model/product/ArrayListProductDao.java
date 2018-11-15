package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ArrayListProductDao arrayListProductDao;
    private List<Product> products;

    private ArrayListProductDao(){}

    public static ArrayListProductDao getInstance(){
        if(arrayListProductDao == null)
            arrayListProductDao = new ArrayListProductDao();
        return arrayListProductDao;
    }
    public int size(){
        return products.size();
    }

    @Override
    public Product getProduct(Long id) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<Product> findProducts() {
        return this.products.stream()
                .filter(x -> (x.getPrice() != null && x.getStock() > 0)).
                        collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public void delete(Long id) {
        throw new RuntimeException("Not implemented");
    }

}
