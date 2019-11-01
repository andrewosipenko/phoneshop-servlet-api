package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exception.ProductNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao productDao;

    public static ProductDao getInstance(){
        if (productDao==null){
            synchronized (ArrayListProductDao.class){
                if (productDao==null)
                    productDao=new ArrayListProductDao();
            }
        }
        return productDao;
    }
    private List<Product> products;

    private ArrayListProductDao(){
        products= new CopyOnWriteArrayList<>();
    }

    @Override
    public Product getProduct(Long id) {
        return products.stream()
                .filter(s->s.getId().equals(id) && s.getPrice()!=null && s.getStock()>0)
                .findAny()
                .orElseThrow(()-> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField,SortOrder sortOrder) {

        List<Product> result =products.stream()
                .filter(s-> s.getPrice()!=null && s.getStock()>0)
                .collect(Collectors.toList());

        if (query!=null) {
            Map<Product,Integer> productsSearch=new TreeMap<>();

            String[] partQuery = query.split(" ");

            result.stream()
                    .filter(s ->
                    {
                        int i = 0;
                        for (String part : partQuery) {
                            if (s.getDescription().contains(part))
                                i++;
                        }
                        if (i == 0) {
                            return false;
                        } else {
                            productsSearch.put(s, i);
                            return true;
                        }
                    })
                    .collect(Collectors.toList());

            productsSearch.entrySet().stream().sorted(Map.Entry.comparingByValue());
            result.clear();
            result.addAll(productsSearch.keySet());
        }
        if (sortField==SortField.PRICE) {
            if (sortOrder == SortOrder.ASC) {
                return result.stream()
                        .sorted(Comparator.comparing(Product::getPrice))
                        .collect(Collectors.toList());
            }

            else {
                return result.stream()
                        .sorted(Comparator.comparing(Product::getPrice, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
            }
        }
        if (sortField==SortField.DESCRIPTION){
            if (sortOrder==SortOrder.ASC) {
                return result.stream()
                        .sorted(Comparator.comparing(Product::getDescription))
                        .collect(Collectors.toList());
            }
            else {
                return result.stream()
                        .sorted(Comparator.comparing(Product::getDescription, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
            }
        }
        return result;

    }

    @Override
    public void save(Product product) {
        if (products.contains(product))
            throw new IllegalArgumentException();
        if (product!=null)
            products.add(product);
    }

    @Override
    public void delete(Long id) {
         products.stream()
                .filter(s -> s.getId().equals(id))
                .findAny()
                .map(s -> products.remove(s))
                .orElseThrow(()->new ProductNotFoundException(id));
    }

    void deleteAll(){
        products.clear();
    }

}


