package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exception.ProductNotFoundException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao productDao;

    public static ProductDao getInstance(){
        if (productDao==null){
            synchronized (ArrayListProductDao.class){
                if (productDao==null) {
                    productDao = new ArrayListProductDao();
                }
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
            result=findProductsByQuery(query, result);
        }
        result=sortProductsList(result,sortField,sortOrder);

        return result;
    }

    private List<Product> findProductsByQuery(String query, List<Product> result){
        String[] partQuery = query.split(" ");
        return result.stream()
                .filter(product -> Arrays.stream(partQuery).anyMatch(p -> product.getDescription().contains(p)))
                .sorted(Comparator.comparing(p->matchCount(p,partQuery),Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private int matchCount(Product product,String[] partQuery){
        return (int) Arrays.stream(partQuery)
                .filter(product.getDescription()::contains)
                .count();
    }

    private Comparator<Product> getComparator(SortField sortField,SortOrder sortOrder){
        if (sortField==SortField.PRICE) {
            if (sortOrder == SortOrder.ASC) {
                return Comparator.comparing(Product::getPrice);
            }
            else {
                return Comparator.comparing(Product::getPrice, Comparator.reverseOrder());
            }
        }
        if (sortField==SortField.DESCRIPTION){
            if (sortOrder==SortOrder.ASC) {
                return Comparator.comparing(Product::getDescription);
            }
            else {
                return Comparator.comparing(Product::getDescription, Comparator.reverseOrder());
            }
        }
        return null;
    }

    private List<Product> sortProductsList(List<Product> productList, SortField sortField, SortOrder sortOrder){
        List<Product> result = productList;
        Comparator<Product> productComparator=getComparator(sortField,sortOrder);
        if (productComparator!=null) {
            result=productList.stream()
                    .sorted(Objects.requireNonNull(getComparator(sortField, sortOrder)))
                    .collect(Collectors.toList());
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


