package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;

public class ArrayListProductDao implements ProductDao {
    private static List<Product> products = new ArrayList<>();
    @Override
    public synchronized Product getProduct(Long id) {
        Product product;
        int index = products.indexOf(new Product(id));
        if (index!=-1){
            product = products.get(index);

        } else{
            return null;
        }
        if (product!=null){
            return product;
        } else {
            return null;
        }
    }

    @Override
    public synchronized List<Product> findProducts() {
        List<Product> filtered = new ArrayList<>();
        products.stream().filter(prod->prod.getPrice()!=null && prod.getStock()>0).forEach(prod->filtered.add(prod));
        return filtered;
    }

    @Override
    public synchronized void save(Product product) {
        if (products.indexOf(product) == -1){
            products.add(product);
        } else{
            products.set(products.indexOf(product),product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        int ind=-1;
        for (Product prd: products) {
            if(prd.getId()==id){
                ind = products.indexOf(prd);
                break;
            }
        }
        if(ind!=-1){
            products.remove(ind);
        }
    }

}
