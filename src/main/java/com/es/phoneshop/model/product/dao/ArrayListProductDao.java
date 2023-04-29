package com.es.phoneshop.model.product.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao instance;
    private final List<Product> products;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private long maxId;

    private ArrayListProductDao() {
        products = new ArrayList<>();
        getSampleProducts();
    }

    public static ProductDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    private Product findOutProductById(Long id) {
        if (id != null) {
            return products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public Product getProduct(Long id) throws ProductException {
        lock.readLock().lock();
        Product findProduct = findOutProductById(id);
        lock.readLock().unlock();
        if (findProduct == null) {
            throw new ProductException(String.format("Product with id= %d wasn't found in the product list", id));
        }
        return findProduct;
    }

    @Override
    public List<Product> findProducts() {
        lock.readLock().lock();
        List<Product> readProducts = products.stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getStock() > 0)
                .collect(Collectors.toList());
        lock.readLock().unlock();
        return readProducts;
    }

    @Override
    public void save(Product product) throws ProductException {
        if (product == null) {
            throw new ProductException("Product to save wasn't defined");
        }

        lock.writeLock().lock();
        try {
            Product currentProduct = findOutProductById(product.getId());
            if (currentProduct != null) {
                products.set(products.indexOf(currentProduct), product);
            } else {
                Product copyProduct = products.stream()
                        .filter(p -> p.equals(product))
                        .findAny()
                        .orElse(null);
                if (copyProduct == null) {
                    product.setId(++maxId);
                    products.add(product);
                } else {
                    throw new ProductException("The product list already has the same product");
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) throws ProductException {
        lock.writeLock().lock();
        try {
            Product deleteProduct = findOutProductById(id);
            if (deleteProduct != null) {
                products.remove(deleteProduct);
            } else {
                throw new ProductException("The product to delete wasn't found in the product list");
            }
        } finally {
            lock.writeLock().unlock();
        }

    }

    private void getSampleProducts() {
        Currency usd = Currency.getInstance("USD");

        try {
            save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
            save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
            save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
            save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
            save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
            save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
            save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
            save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
            save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
            save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
            save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
            save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
            save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

}
