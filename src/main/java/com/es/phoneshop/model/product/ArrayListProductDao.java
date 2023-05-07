package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private Long productId;
    private final List<Product> products;
    private final Lock readLock;
    private final Lock writeLock;

    public ArrayListProductDao() {
        this.productId = 1L;
        products = new ArrayList<>();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
        initProducts();
    }

    @Override
    public Product getProduct(Long id) {
        try {
            readLock.lock();
            return getOptionalOfProduct(id)
                    .orElseThrow(() -> new RuntimeException("No such element was found"));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Product> findProducts() {
        try {
            readLock.lock();
            return products.stream()
                    .filter(this::productInStock)
                    .filter(this::productPriceNotNull)
                    .collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    private boolean productPriceNotNull(Product product) {
        return product.getPrice() != null;
    }

    private boolean productInStock(Product product) {
        return product.getStock() > 0;
    }

    @Override
    public void save(Product product) {
        if (product == null) {
            throw new RuntimeException("Product is null");
        }

        try {
            writeLock.lock();

            if (product.getId() == null) {
                saveProduct(product);
                return;
            }
            Optional<Product> optional = getOptionalOfProduct(product.getId());
            if (optional.isEmpty()) {
                saveProduct(product);
            } else {
                changeProduct(optional.get(), product);
            }
        } finally {
            writeLock.unlock();
        }
    }

    private void saveProduct(Product product) {
        product.setId(productId++);
        products.add(product);
    }

    /**
     * this method uses inner class and replaces old values of existed product with new
     */
    private void changeProduct(Product existed, Product product) {
        existed.changer()
                .code(product.getCode())
                .imageUrl(product.getImageUrl())
                .currency(product.getCurrency())
                .price(product.getPrice())
                .description(product.getDescription())
                .stock(product.getStock());
    }

    /**
     * return optional of product by id for next checking of existence
     */
    private Optional<Product> getOptionalOfProduct(Long id) {
        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny();
    }

    @Override
    public void delete(Long id) {
        try {
            writeLock.lock();
            Optional<Product> optional = getOptionalOfProduct(id);

            if (optional.isEmpty()) {
                throw new RuntimeException("No such element was found");
            }
            products.remove(optional.get());
        } finally {
            writeLock.unlock();
        }
    }

    private void initProducts() {
        Currency usd = Currency.getInstance("USD");
        products.add(new Product(productId++, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        products.add(new Product(productId++, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        products.add(new Product(productId++, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        products.add(new Product(productId++, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        products.add(new Product(productId++, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        products.add(new Product(productId++, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        products.add(new Product(productId++, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        products.add(new Product(productId++, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        products.add(new Product(productId++, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        products.add(new Product(productId++, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        products.add(new Product(productId++, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        products.add(new Product(productId++, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        products.add(new Product(productId++, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }
}
