package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ArrayListProductDao implements ProductDao{
    ArrayList<Product> products;

   public ArrayListProductDao(){
        products = new ArrayList<Product>();
        Product pr1 = new Product(new Long(2),"12", "iPhone",new BigDecimal(10.0), 2);
       Product pr2 = new Product(new Long(5),"42", "iPhone5",new BigDecimal(0), 1);
       Product pr3 = new Product(new Long(7),"phone", "iPhoneX",new BigDecimal(100.0), 1);

       this.save(pr1);
       this.save(pr2);
       this.save(pr3);
    }


    public Product getProduct(Long id) {
       synchronized (products) {
           for (Product p : products) {
               if (p.getId() == id) {
                   return p;
               }
           }
       }
       return null;
    }

    public List<Product> findProducts() {
        ArrayList<Product> result = new ArrayList<Product>();
        synchronized (products) {
             return products.parallelStream().filter(s->(s.getPrice().doubleValue() > 0 && s.getStock() != 0)).collect(Collectors.toList());
        }
    }

    public void save(Product product) {
       synchronized (products) {
           products.add(product);
       }
    }
    public void delete(Long id) {
       synchronized (products) {
           products.parallelStream().filter(s -> s.getId() == id).limit(1).forEach(s -> products.remove(s));
       }
    }
}
