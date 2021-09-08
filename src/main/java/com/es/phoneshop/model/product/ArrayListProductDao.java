package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> result;
    private long maxId;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public ArrayListProductDao() {
        result = new ArrayList<>();
        saveSampleProducts();
    }

    private void saveSampleProducts() {
        lock.writeLock().lock();
        Currency usd = Currency.getInstance("USD");
        saveProduct(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        saveProduct(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        saveProduct(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        saveProduct(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        saveProduct(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        saveProduct(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        saveProduct(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        saveProduct(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        saveProduct(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        saveProduct(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        saveProduct(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        saveProduct(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        saveProduct(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
        lock.writeLock().unlock();
    }

    @Override
    public Product getProduct(Long id) throws NoSuchElementException {
        lock.readLock().lock();
        try {
            return result.stream().
                    filter(product -> product.getId().equals(id)).
                    findAny().
                    orElseThrow(NoSuchElementException::new);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(List<String> searchTextList, SortField sortField, SortOrder sortOrder) {
        lock.readLock().lock();
        try {
            List<Product> products = result.stream().
                    filter(product -> searchTextList == null || searchTextList.isEmpty() || searchTextList.stream().anyMatch(searchText ->
                            product.getDescription().toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT)))).
                    filter(product -> product.getPrice() != null).
                    filter(product -> product.getStock() > 0).
                    collect(Collectors.toList());
            return sortProducts(products, sortField, sortOrder);
        } finally {
            lock.readLock().unlock();
        }
    }

    private List<Product> sortProducts(List<Product> products, SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator;
        if (sortField == SortField.price) {
            comparator = Comparator.comparing(Product::getPrice);
        } else if (sortField == SortField.description) {
            comparator = Comparator.comparing(Product::getDescription);
        } else {
            return products;
        }
        products = products.stream().
                sorted(comparator).
                collect(Collectors.toList());
        return (sortOrder == SortOrder.asc) ?
                products :
                products.stream().sorted(comparator.reversed()).collect(Collectors.toList());
    }

    @Override
    public synchronized void saveProduct(Product product) {
        if (product.getId() == null) {
            product.setId(maxId++);
            result.add(product);
        } else {
            try {
                getProduct(product.getId());
                long productId = product.getId();
                deleteProduct(productId);
                product.setId(productId);
                result.add(product);
            } catch (NoSuchElementException exception) {
                product.setId(maxId++);
                result.add(product);
            }
        }
    }

    @Override
    public void deleteProduct(Long id) throws NoSuchElementException {
        lock.readLock().lock();
        if (result.stream().anyMatch(getProduct(id)::equals)) {
            lock.readLock().unlock();
            lock.writeLock().lock();
            result.remove(getProduct(id));
            lock.writeLock().unlock();
        } else {
            throw new NoSuchElementException();
        }
    }
}
