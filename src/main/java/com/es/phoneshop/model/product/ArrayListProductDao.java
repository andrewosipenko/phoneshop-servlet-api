package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class ArrayListProductDao implements ProductDao {
    private static volatile ArrayListProductDao instance;
    private ArrayList<Product> products;

    public ArrayListProductDao() {
        products = new ArrayList<>();
    }

    public static ArrayListProductDao getInstance(){
        if(instance == null)
            synchronized (ArrayListProductDao.class){
                if(instance == null)
                    instance = new ArrayListProductDao();
            }
        return instance;
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter((product) -> product.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public synchronized List<Product> findProducts(String query, String sortField, String order) {

        List<Product> shownProducts;
        if(query!= null) {
            String queryInLowerCase = query.toLowerCase();
            Map<Product, Integer> mapOfProducts = new HashMap<>();
            products.stream()
                    .forEach(product -> mapOfProducts.put(product, 0));

            String[] words = queryInLowerCase.split("\\s");
            for(String word : words) {
                products.stream().forEach(
                        product -> {
                            if (product.getDescription() != null && product.getDescription().toLowerCase().contains(word)){
                                mapOfProducts.put(product, mapOfProducts.get(product) + 1);
                            }
                        });
            }
            //sort map by values
            Map<Product, Integer> sortedMap = mapOfProducts
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(
                            Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

            shownProducts = new ArrayList<>();
            sortedMap.entrySet().stream().forEach(
                    elemOfMap -> {
                        if(elemOfMap.getValue() > 0)
                        shownProducts.add(elemOfMap.getKey());
                    });

            Collections.reverse(shownProducts);

        }else shownProducts = products;

        if(sortField != null && order != null){
            if(sortField.equals("description")) {
                if (order.equals("asc")) {
                    shownProducts.sort(Comparator.comparing(Product::getDescription));
                } else if (order.equals("desc")) {
                    shownProducts.sort(Comparator.comparing(Product::getDescription).reversed());
                }
            }else if(sortField.equals("price")){
                if (order.equals("asc")) {
                    shownProducts.sort(Comparator.comparing(Product::getPrice));
                } else if (order.equals("desc")) {
                    shownProducts.sort(Comparator.comparing(Product::getPrice).reversed());
                }
            }
        }

        return shownProducts.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (products.stream()
                .noneMatch(product1 -> product1.getId().equals(product.getId()))) {
            products.add(product);
        } else {
            throw new RuntimeException("There is product with the same id in the shoplist");
        }

    }

    @Override
    public synchronized void delete(Long id) {
        for(Product pr : products){
            if(pr.getId().equals(id)){
                products.remove(pr);
                return;
            }
        }
        throw new RuntimeException();
    }

}
