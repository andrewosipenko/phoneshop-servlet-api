package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static List<Product> products = new ArrayList<>();

    private static volatile ArrayListProductDao instance;

    private ArrayListProductDao() {

    }

    public static ArrayListProductDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
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
    public synchronized List<Product> findProducts(String query, String sortField, String order) {
        List<Product> sortedList;
        if (query != null && query != "") {
            Map<Product, Integer> productsMap = new HashMap<>();
            for (Product product : products) {
                productsMap.put(product, 0);
            }
            String[] words = query.toLowerCase().split("\\s");
            for (String word : words) {
                for (Product product :
                        products.stream().
                                filter(x -> x.getDescription().toLowerCase().contains(word)).
                                collect(Collectors.toList())) {
                    productsMap.put(product, productsMap.get(product) + 1);

                    String[] wordsFromDescription = product.getDescription().toLowerCase().split("\\s");
                    for (String description : wordsFromDescription) {
                        if (description.equals(word)) {
                            productsMap.put(product, productsMap.get(product) + 10); //whole word has higher priority, than character
                        }
                    }
                }
            }
            productsMap = productsMap.entrySet().stream().
                    filter(x -> x.getValue() > 0).
                    sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                    collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));
            sortedList = productsMap.keySet().stream().collect(Collectors.toList());
        } else {
            sortedList = products;
        }

        if (sortedList == null || order == null || sortField == "" || order == "") {
            return sortedList.stream().
                    filter(x -> (x.getPrice() != null && x.getStock() > 0)).
                    collect(Collectors.toList());
        } else {
            Comparator<Product> comparator;
            Map<String, Comparator> sortMap = new HashMap<>();
            sortMap.put("description", Comparator.comparing(Product::getDescription));
            sortMap.put("price", Comparator.comparing(Product::getPrice));
            comparator = sortMap.get(sortField);
            if (order.equals("desc")){
                comparator = comparator.reversed();
            }
            return sortedList.stream().
                    filter(x -> (x.getPrice() != null && x.getStock() > 0)).
                    sorted(comparator).
                    collect(Collectors.toList());
        }
}


    @Override
    public synchronized void save(Product product) {
        if (products.stream().
                noneMatch(product1 -> product1.getId().
                        equals(product.getId()))) {
            products.add(product);
        } else {
            products.remove(product.getId());
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        Product product = getProduct(id);
        if (product != null) {
            products.remove(product);
        } else {
            throw new NoSuchElementException("Does not contain such product");
        }
    }
}
