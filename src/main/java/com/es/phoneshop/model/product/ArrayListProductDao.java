package com.es.phoneshop.model.product;

import org.apache.commons.collections.ComparatorUtils;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao, TestableSingletonProductDao<List<Product>> {
    private static volatile ArrayListProductDao instance;

    private long maxID;

    private List<Product> products;

    private final ReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;

    {
        readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    private ArrayListProductDao(List<Product> products) {
        this.products = products;
    }

    //threadsafe singleton with DCL
    public static ArrayListProductDao getInstance() {
        ArrayListProductDao result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ArrayListProductDao.class) {
            if (instance == null) {
                instance = new ArrayListProductDao();
            }
            return instance;
        }
    }

    public static ArrayListProductDao getInstance(List<Product> products) {
        ArrayListProductDao result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ArrayListProductDao.class) {
            if (instance == null) {
                instance = new ArrayListProductDao(products);
            }
            return instance;
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        readLock.lock();
        try {
            return products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Product> getAll() {
        readLock.lock();
        try {
            return products;
        } finally {
            readLock.unlock();
        }
    }

    //in my humble opinion it is wrong implementation of create/update
    @Override
    public void save(Product product) {
        writeLock.lock();
        try {
            product.setId(++maxID);
            products.stream()
                    .filter(productFromList -> productFromList.getId().equals(product.getId()))
                    .findAny()
                    .ifPresentOrElse
                    //if product already exist in collection swap them
                            (productFromList -> {
                                        products.remove(productFromList);
                                        products.add(product);
                                    },
                                    //else if product isn't exist in collection simply add it
                                    () -> products.add(product));
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public void delete(Long id) {
        writeLock.lock();
        try {
            products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findFirst()
                    .ifPresent(products::remove);
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public List<Product> find(String query) {
        readLock.lock();
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
        //!product1Description.contains(term) && !product2Description.contains(term) ||
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
