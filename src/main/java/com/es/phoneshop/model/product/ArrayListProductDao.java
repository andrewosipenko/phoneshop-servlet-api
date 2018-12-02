package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;


public class ArrayListProductDao implements ProductDao {
    private static volatile ArrayListProductDao instance;
    private ArrayList<Product> products;
    private boolean isSorted = false;
    private Map<String, Comparator<Product>> mapForComparing;

    private ArrayListProductDao() {

        products = new ArrayList<>();

        mapForComparing = new HashMap<>();
        mapForComparing.put("description", Comparator.comparing(Product::getDescription));
        mapForComparing.put("price", Comparator.comparing(Product::getPrice));
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
            products.forEach(product -> mapOfProducts.put(product, 0));

            String[] words = queryInLowerCase.split("\\s");
            for(String word : words) {
                products.forEach(product -> {
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
            sortedMap.forEach((key, value) -> {
                if (value > 0)
                    shownProducts.add(key);
            });

            Collections.reverse(shownProducts);

        }else shownProducts = products;

        if(sortField != null && order != null){
            if (order.equals("asc")){
                shownProducts.sort(mapForComparing.get(sortField));
            }else if (order.equals("desc")) {
                shownProducts.sort(mapForComparing.get(sortField).reversed());
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
