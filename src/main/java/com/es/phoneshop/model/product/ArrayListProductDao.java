package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private Comparator<Product> comparator;
    private Map<String, Comparator<Product>> sortMap;
    private static List<Product> products = new ArrayList<>();

    private static volatile ArrayListProductDao instance;

    private ArrayListProductDao() {
        sortMap = new HashMap<>();
        sortMap.put("description", Comparator.comparing(Product::getDescription));
        sortMap.put("price", Comparator.comparing(Product::getPrice));
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
    public Product getProduct(Long id) {
        return products
                .stream()
                .filter(product1 -> product1.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public List<Product> findProducts(String query, String sortField, String order) {
        List<Product> sortedList;
        if (query != null && !query.equals("")) {
            Map<Product, Integer> productsMap = new HashMap<>();
            for (Product product : products) {
                productsMap.put(product, 0);
            }
            String[] words = query.toLowerCase().split("\\s");
            for (String word : words) {
                for (Product product :
                        products.stream()
                                .filter(x -> x.getDescription().toLowerCase().contains(word))
                                .collect(Collectors.toList())) {
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
                    filter(x -> x.getValue() > 0)
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));
            sortedList = new ArrayList<>(productsMap.keySet());
        } else {
            sortedList = products;
        }

        if (sortField == null || order == null || sortField.equals("") || order.equals("")) {
            return sortedList.stream()
                    .filter(x -> (x.getPrice() != null && x.getStock() > 0))
                    .collect(Collectors.toList());
        } else {
            comparator = sortMap.get(sortField);
            if (order.equals("desc")) {
                comparator = comparator.reversed();
            }
            return sortedList.stream()
                    .filter(x -> (x.getPrice() != null && x.getStock() > 0))
                    .sorted(comparator)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void save(Product product) {
        if (products.stream()
                .noneMatch(product1 -> product1.getId()
                        .equals(product.getId()))) {
            products.add(product);
        } else {
            products.remove(product);
            products.add(product);
        }
    }

    @Override
    public void delete(Long id) {
        Product product = getProduct(id);
        if (product != null) {
            products.remove(product);
        } else {
            throw new NoSuchElementException("Does not contain such product");
        }
    }
}
