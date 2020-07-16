package com.es.phoneshop.model.product;

import org.apache.commons.collections.ComparatorUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private long maxID;

    private final List<Product> products;

    private final ReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;

    {
        readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    public ArrayListProductDao() {
        this.products = new ArrayList<>();
        getSampleProducts();
    }

    public ArrayListProductDao(List<Product> products){
        this.products = products;
    }

    @Override
    public Optional<Product> get(Long id)  {
        readLock.lock();
        try {
            return products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny();
        }
        finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Product> getAll() {
        readLock.lock();
        try {
            return products;
        }
        finally {
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
                            (productFromList->{
                                products.remove(productFromList);
                                products.add(product);
                            },
                            //else if product isn't exist in collection simply add it
                            ()-> products.add(product));
        }
        finally {
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
        }
        finally {
            writeLock.unlock();
        }

    }

    @Override
    public List<Product> find(String query) {
        readLock.lock();
        try {
            List<Comparator<Product>> comparators = new LinkedList<>();
            if(query != null){
                String[] terms = query.toLowerCase().split(" ");
                Arrays.stream(terms)
                        .forEach(term -> comparators.add(this.getDescriptionContainingComparator(term)));
                return this.products.stream()
                        .sorted(((Comparator<Product>) ComparatorUtils.chainedComparator(comparators)))
                        .filter(product -> isPartlyContaining(product.getDescription(), terms))
                        .collect(Collectors.toList());
            } else return products;
        } finally {
            readLock.unlock();
        }
    }

    //all methods without locks are reentrant
    private boolean isPartlyContaining(String string, String[] terms){
        for (var term : terms) {
            if(string.toLowerCase().contains(term)){
                return true;
            }
        }
        return false;
    }

    private Comparator<Product> getDescriptionContainingComparator(String rawTerm){
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

    private boolean onlySecondContainsTerm(String first, String second, String term){
        return !first.contains(term) && second.contains(term);
    }

    private boolean areBothContainingTerm(String first, String second, String term){
        //!product1Description.contains(term) && !product2Description.contains(term) ||
        return first.contains(term) && second.contains(term);
    }


    //todo move into DataSampleListener
    private void getSampleProducts(){
        Currency usd = Currency.getInstance("USD");
        this.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        this.save(new Product( "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        this.save(new Product( "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        this.save(new Product( "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        this.save(new Product( "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        this.save(new Product( "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        this.save(new Product( "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        this.save(new Product( "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        this.save(new Product( "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        this.save(new Product( "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        this.save(new Product( "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        this.save(new Product( "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        this.save(new Product( "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }
}
