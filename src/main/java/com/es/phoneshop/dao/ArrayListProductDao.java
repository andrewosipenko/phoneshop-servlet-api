package com.es.phoneshop.dao;

import com.es.phoneshop.model.SortField;
import com.es.phoneshop.model.SortOrder;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> productList;
    private long productId;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public ArrayListProductDao() {
        productList = getSampleProducts();
    }

    protected void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    private int wordsAmount(List<String> wordsList, Product product) {
        String description = product.getDescription();
        int amount = 0;
        for (String word : wordsList) {
            if (description.contains(word)) {
                amount++;
            }
        }
        return amount;
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            return productList.stream()
                    .filter((product) -> id.equals(product.getId()))
                    .findAny()
                    .orElseThrow(ProductNotFoundException::new);
        }
        finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Product> findProducts(String queryProduct, SortField sortField, SortOrder sortOrder) {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            List<String> wordsList = new ArrayList<>();
            if (queryProduct != null && !queryProduct.isEmpty()) {
                wordsList = Arrays.asList(queryProduct.split(" "));
            }
            List<String> finalWordsList = wordsList;
            Comparator comparator = Comparator.comparing(((product -> {
                        if (sortField == SortField.description) {
                            return (Comparable)((Product) product).getDescription();
                        } else {
                            if (sortField == SortField.price) {
                                return (Comparable) ((Product) product).getPrice();
                            } else {
                                return (Comparable) 1;
                            }
                        }
                    })));

            List<Product> searchResults = productList.stream()
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    .filter(product -> queryProduct == null || queryProduct.isEmpty() || wordsAmount(finalWordsList, product) > 0)
                    .sorted(Comparator.comparing(product -> wordsAmount(finalWordsList, product), Comparator.reverseOrder()))
                    .collect(Collectors.toList());

            if (sortOrder == SortOrder.desc) {
                return (List<Product>) searchResults.stream()
                        .sorted(comparator.reversed())
                        .collect(Collectors.toList());
            }
            else {
                return (List<Product>) searchResults.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
            }
        }
        finally {
            readLock.unlock();
        }
    }

    @Override
    public void save(Product product) throws ProductNotFoundException {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Long id = product.getId();
            if (id != null) {
                productList.remove(getProduct(id));
                productList.add(product);
            }
            else {
                product.setId(++productId);
                productList.add(product);
            }
        }
        finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(Long id) throws ProductNotFoundException {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Product product = getProduct(id);
            productList.remove(product);
        }
        finally {
            writeLock.unlock();
        }
    }

    public List<Product> getSampleProducts() {
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        result.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        result.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        result.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        result.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        result.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        result.add(new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        result.add(new Product(7L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        result.add(new Product(8L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        result.add(new Product(9L, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        result.add(new Product(10L, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        result.add(new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        result.add(new Product(12L, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        result.add(new Product(13L, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
        productId = 13L;

        return result;
    }

}
