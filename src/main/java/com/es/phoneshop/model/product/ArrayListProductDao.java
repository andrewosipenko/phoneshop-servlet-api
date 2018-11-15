package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static List<Product> products = new ArrayList<>() ;

    private static volatile ArrayListProductDao instance /*= new ArrayListProductDao()*/;

    private ArrayListProductDao() {

    }
    public static ArrayListProductDao getInstance() {
        if(instance == null) {
            synchronized (ArrayListProductDao.class) {
                if(instance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }


    @Override
    public synchronized Product getProduct(Long id) {
        Product product = null;
        product = products
                .stream()
                .filter(product1 -> product1.getId().equals(id))
                .findFirst()
                .get();
        return product;
    }

    @Override
    public synchronized List<Product> findProducts() {
        List<Product> result = products.stream().
                filter(product -> product.getPrice() != null && product.getStock() > 0).
                collect(Collectors.toCollection(ArrayList::new));
        return result;
    }

    @Override
    public synchronized void save(Product product) {
        if (products.stream()
                .noneMatch(product1 -> product1.getId().
                        equals(product.getId()))) {
            products.add(product);
        } else {
            throw new NullPointerException("There is product with such id in the shoplist");
        }
    }

    @Override
    public synchronized void delete(Long id) {
        Product product = getProduct(id);
        if (product != null) {
            products.remove(product);
        } else throw new NoSuchElementException("Does not contain such product");
    }


}
