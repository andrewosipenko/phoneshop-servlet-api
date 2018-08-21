package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ArrayListProductDao implements ProductDao{
    private ArrayList<Product> products;

   public ArrayListProductDao(){
        products = new ArrayList<Product>();
        save(new Product(2L,"12", "iPhone",new BigDecimal(10.0), 2));
       save( new Product(5L,"42", "iPhone5",new BigDecimal(0), 1));
       save( new Product(7L,"phone", "iPhoneX",new BigDecimal(100.0), 1));
    }


    public synchronized Product getProduct(Long id) {
          return products.parallelStream().filter(s->s.getId() == id).findAny().get();
    }

    public synchronized List<Product> findProducts() {
        ArrayList<Product> result = new ArrayList<Product>();
             return products.parallelStream().filter(s->(s.getPrice()
                     .doubleValue() > 0 && s.getStock() != 0))
                     .collect(Collectors.toList());
    }

    public synchronized void save(Product product) {
           products.add(product);
    }
    public synchronized void delete(Long id) {
           products.remove(getProduct(id));
    }
}
