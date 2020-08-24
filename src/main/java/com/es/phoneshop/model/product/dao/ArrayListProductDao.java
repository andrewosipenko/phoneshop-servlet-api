package com.es.phoneshop.model.product.dao;

import com.es.phoneshop.model.GenericArrayListDao;
import com.es.phoneshop.model.product.entity.Product;
import org.apache.commons.collections.ComparatorUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao extends GenericArrayListDao<Product> implements TestableSingletonProductDao<List<Product>> {

    private static class SingletonHelper {
        private static final ArrayListProductDao INSTANCE = new ArrayListProductDao();
    }

    public static ArrayListProductDao getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public List<Product> find(String query) {
        super.readLock.lock();
        try {
            List<Comparator<Product>> comparators = new LinkedList<>();
            if (query != null && !query.equals(" ")) {
                String[] terms = query.toLowerCase().split(" ");
                Arrays.stream(terms)
                        .forEach(term -> comparators.add(this.getDescriptionContainingComparator(term)));

                return this.products.stream()
                        .filter(product -> isPartlyContaining(product.getDescription(), terms))
                        .sorted(((Comparator<Product>) ComparatorUtils.chainedComparator(comparators)))
                        .collect(Collectors.toList());
            } else return products;
        } finally {
            readLock.unlock();
        }
    }

    //all methods without locks are reentrant
    private boolean isPartlyContaining(String string, String[] terms) {
        for (var term : terms) {
            if (string.toLowerCase().contains(term)) {
                return true;
            }
        }
        return false;
    }

    private Comparator<Product> getDescriptionContainingComparator(String rawTerm) {
        return (product1, product2) -> {
            String product1Description = product1.getDescription().toLowerCase();
            String product2Description = product2.getDescription().toLowerCase();
            String term = rawTerm.toLowerCase();

            if (onlySecondContainsTerm(product1Description, product2Description, term)) {
                return 1;
            } else if (areBothContainingTerm(product1Description, product2Description, term)) {
                return 0;
            } else return -1;
        };
    }

    private boolean onlySecondContainsTerm(String first, String second, String term) {
        return !first.contains(term) && second.contains(term);
    }

    private boolean areBothContainingTerm(String first, String second, String term) {
        return first.contains(term) && second.contains(term);
    }


    @Override
    public List<Product> get() {
        return products;
    }

    @Override
    public void set(List<Product> resource) {
        products = resource;
    }

    @Override
    public void dropToDefault() {
        products = new ArrayList<>();
        maxID = 0;
    }
}
