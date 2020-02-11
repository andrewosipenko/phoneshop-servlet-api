package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> productList;
    private Long maxId;
    public ArrayListProductDao() {
        this.productList = new ArrayList<>();
        maxId = 0L;
        init();
    }

    public void init() {
        Currency usd = Currency.getInstance("USD");
        this.save(new Product(null, "sgs",
                "Samsung Galaxy S", new BigDecimal(100), usd, 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        this.save(new Product(null, "sgs2",
                "Samsung Galaxy S II", new BigDecimal(200), usd, 0,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        this.save(new Product(null, "sgs3",
                "Samsung Galaxy S III", new BigDecimal(300), usd, 5,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        this.save(new Product(null, "iphone",
                "Apple iPhone", new BigDecimal(200), usd, 10,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        this.save(new Product(null, "iphone6",
                "Apple iPhone 6", new BigDecimal(1000), usd, 30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        this.save(new Product(null, "htces4g",
                "HTC EVO Shift 4G", new BigDecimal(320), usd, 3,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        this.save(new Product(null, "sec901",
                "Sony Ericsson C901", new BigDecimal(420), usd, 30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        this.save(new Product(null, "xperiaxz",
                "Sony Xperia XZ", new BigDecimal(120), usd, 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        this.save(new Product(null, "nokia3310",
                "Nokia 3310", new BigDecimal(70), usd, 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        this.save(new Product(null, "palmp",
                "Palm Pixi", new BigDecimal(170), usd, 30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        this.save(new Product(null, "simc56",
                "Siemens C56", new BigDecimal(70), usd, 20,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        this.save(new Product(null, "simc61",
                "Siemens C61", new BigDecimal(80), usd, 30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        this.save(new Product(null, "simsxg75",
                "Siemens SXG75", new BigDecimal(150), usd, 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));

    }

    @Override
    public Product getProduct(Long id) {
        if (productList != null) {
            return productList.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public List<Product> findProducts() {
        if (productList == null) {
            return null;
        }
        return productList.stream().filter(product -> (product.getStock() > 0)).filter(product->product.getPrice() != null)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product.getId()==null) {
            product.setId(++maxId);
            productList.add(product);
        }
        else {
            productList.add(product);
        }
    }

    private void update(Product product, Product newProduct) {
        newProduct.setCode(product.getCode());
        newProduct.setCurrency(product.getCurrency());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setImageUrl(product.getImageUrl());
        newProduct.setStock(product.getStock());
    }

    @Override
    public synchronized void delete(Long id) {
        productList.removeIf(p -> Objects.equals(p.getId(), id));
    }

}
