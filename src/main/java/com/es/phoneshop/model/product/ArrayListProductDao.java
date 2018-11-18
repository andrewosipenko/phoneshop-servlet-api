package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ArrayListProductDao arrayListProductDao;
    private List<Product> products = new CopyOnWriteArrayList<>();

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
        for (Product product: products
             ) {
            if(product.getId().equals(id))
                return product;
        }
        return null;
    }

    @Override
    public List<Product> findProducts(String query, String sortField, String order) {
        List<Product> help = new ArrayList<>();
        if(query!= null) {
            String[] words = query.split("\\s");
            for (String word :
                    words) {
                help.addAll(this.products.stream().filter(x ->  x.getDescription().contains(word)).collect(Collectors.toList()));

            }
            help = help.stream().distinct().collect(Collectors.toList());
        }
        else help = this.products;
        return help.stream()
                .filter(x -> (x.getPrice() != null && x.getStock() > 0)).
                        sorted(new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                if (sortField != null && order != null) {
                                    if (sortField.equals("description")) {
                                        if (order.equals("asc")) {
                                            return o1.getDescription().compareTo(o2.getDescription());
                                        } else if (order.equals("desc")) {
                                            return -o1.getDescription().compareTo(o2.getDescription());
                                        }
                                    } else if (sortField.equals("price")) {
                                        if (order.equals("asc")) {
                                            return o1.getPrice().compareTo(o2.getPrice());
                                        } else if (order.equals("desc")) {
                                            return -o1.getPrice().compareTo(o2.getPrice());
                                        }
                                    }
                                    return 0;
                                }
                                return 0;
                            }

                        }).
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