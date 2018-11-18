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
        Set<String> querySet = stringToSet(query);
        return products.stream().
                filter(product -> product.getPrice() != null && product.getStock() > 0).
                filter(product -> query == null || query == "" ||
                        stringToSet(product.getDescription().toLowerCase()).removeAll(querySet)).
                sorted(new ProductComparator(querySet)).
                sorted(new ProductSortComparator(sortField, order)).
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
            if (string != null) {
                String str = string.toLowerCase();
                StringTokenizer st = new StringTokenizer(str, "  \t\n\r,:-");
                while (st.hasMoreTokens()) {
                    stringSet.add(st.nextToken());
                }
            }

        return stringSet;
    }

    class ProductComparator implements Comparator<Product> {
        private Set<String> querySet;

        public ProductComparator (Set<String> querySet) {
            this.querySet = querySet;
        }

        @Override
        public int compare (Product o1, Product o2) {
            Set<String> firstSet = stringToSet(o1.getDescription().toLowerCase());
            firstSet.retainAll(querySet);
            Set<String> secondSet = stringToSet(o2.getDescription().toLowerCase());
            secondSet.retainAll(querySet);
            if (firstSet.size() == secondSet.size() && querySet.size() > 0) {
              if (firstSet.toArray()[0] == querySet.toArray()[0] && secondSet.toArray()[0] != querySet.toArray()[0])
                  return 1;
              else if (firstSet.toArray()[0] == querySet.toArray()[0] && secondSet.toArray()[0] != querySet.toArray()[0])
                  return 0;
              else return -1;
            }
            return secondSet.size() - firstSet.size();


        }
    }

    class ProductSortComparator implements Comparator<Product>{
        private String sortField;
        private String order;

        public ProductSortComparator (String sortField, String order) {
            this.sortField = sortField;
            this.order = order;
        }

        @Override
        public int compare (Product o1, Product o2) {
            if (sortField == null || order == null)
                return 0;
            else if (sortField.equals("description")) {
                if (order.equals("asc")) {
                    return o1.getDescription().compareTo(o2.getDescription());
                }
                else return o2.getDescription().compareTo(o1.getDescription());
            }
            else {
                if (order.equals("asc")){
                    return o1.getPrice().intValue() - o2.getPrice().intValue();
                }
                else
                    return o2.getPrice().intValue() - o1.getPrice().intValue();
            }
        }
    }

}
