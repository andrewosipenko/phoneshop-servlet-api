package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayListProductDao implements ProductDao {
    private final List<Product> products;

    public ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    public ArrayListProductDao(List<Product> products){
        this.products = products;
    }

    //is it better then return optional?
    @Override
    public Product get(Long id) throws NoSuchElementException {
        return products.stream().
                filter(product -> id.equals(product.getId())).
                findAny().
                get();
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    //in my humble opinion it is wrong implementation of create/update
    //semantically it's probably wrong usage of orElseGet, but pretty compact
    @Override
    public void save(Product product) {
         products.stream().
                 filter(productFromList -> productFromList.getId().equals(product.getId())).
                 findAny().
                 orElseGet(() -> {
                     products.add(product);
                     return product;
         });
    }

    //imho lazy foreach could be better
    @Override
    public void delete(Long id) {
        products.stream().
                filter(product -> id.equals(product.getId())).
                findFirst().
                ifPresent(products::remove);
    }


}
