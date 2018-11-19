package com.es.phoneshop.model.product;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao object;
    private List<Product> products;
    private Currency usd = Currency.getInstance("USD");

    private ArrayListProductDao() {
        products = new ArrayList<>(13);
        products.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        products.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        products.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        products.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        products.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        products.add(new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        products.add(new Product(7L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        products.add(new Product(8L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        products.add(new Product(9L, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        products.add(new Product(10L, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        products.add(new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        products.add(new Product(12L, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        products.add(new Product(13L, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

    public static ArrayListProductDao getObject() {
        if (object == null) {
            synchronized (ArrayListProductDao.class) {
                if (object == null) {
                    object = new ArrayListProductDao();
                }
            }

        }
        return object;
    }

    @Override
    public Product getProduct(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id))
                return product;
        }
        throw new RuntimeException("Product with " + id + " was not founded!");
    }

    @Override
    public List<Product> findProducts() {
        return products.stream().filter(x -> x.getPrice() != null)
                .filter(x -> x.getStock() > 0).collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        if (product.getStock() > 0 && product.getPrice() != null) {
            products.add(product);
        }
    }

    @Override
    public void delete(Long id) {
        boolean flagId = false;
        synchronized (this) {
           //products.removeIf(x->x.getId().equals(id));
            for (Product product : products) {
                if (product.getId().equals(id)) {
                    products.remove(product);
                    flagId = true;
                }
            }
        }
        if(!flagId){
            throw new RuntimeException("Product with id: " + id + " was not founded");
        }
    }

}


