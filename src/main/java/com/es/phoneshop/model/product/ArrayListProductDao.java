package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static List<Product> products = new ArrayList<>();

    private static volatile ArrayListProductDao instance /*= new ArrayListProductDao()*/;

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
    public synchronized List<Product> findProducts(String query) {
        /*String string = query.toLowerCase();
        String str = string.replaceAll("\\s","|");
        String string1 = ".*("+str+").*";*/

        Set<String> querySet = stringToSet(query.toLowerCase());
        return products.stream().
                filter(product -> product.getPrice() != null && product.getStock() > 0).
               // filter(product -> str == null || product.getDescription().matches(string1)).
                filter(product -> (query == "") || stringToSet(product.getDescription().toLowerCase()).removeAll(querySet)).
                sorted(new ProductComparator(querySet)).
                collect(Collectors.toList());
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
        } else {
            throw new NoSuchElementException("Does not contain such product");
        }
    }

    private Set<String> stringToSet(String string) {
        Set<String> stringSet = new HashSet<>();
        StringTokenizer st = new StringTokenizer(string, "  \t\n\r,:-");
        while (st.hasMoreTokens()) {
            stringSet.add(st.nextToken());
        }
        return stringSet;
    }

    class ProductComparator implements Comparator<Product> {
        private Set<String> querySet;

        @Override
        public int compare(Product o1, Product o2) {
            Set<String> firstSet = stringToSet(o1.getDescription().toLowerCase());
            firstSet.retainAll(querySet);
            Set<String> secondSet = stringToSet(o2.getDescription().toLowerCase());
            secondSet.retainAll(querySet);

            return (-firstSet.size() + secondSet.size());

        }

        public ProductComparator(Set<String> querySet){
            this.querySet = querySet;

        }
    }

}
