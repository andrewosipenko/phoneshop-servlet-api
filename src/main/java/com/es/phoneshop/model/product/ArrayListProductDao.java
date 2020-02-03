package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    List<Product> productList;

    public ArrayListProductDao() {
        this.productList = new ArrayList<>();
        init();
    }

    public void init() {
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "sgs",
                "Samsung Galaxy S", new BigDecimal(100), usd, 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "sgs2",
                "Samsung Galaxy S II", new BigDecimal(200), usd, 0,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "sgs3",
                "Samsung Galaxy S III", new BigDecimal(300), usd, 5,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "iphone",
                "Apple iPhone", new BigDecimal(200), usd, 10,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "iphone6",
                "Apple iPhone 6", new BigDecimal(1000), usd, 30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "htces4g",
                "HTC EVO Shift 4G", new BigDecimal(320), usd, 3,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "sec901",
                "Sony Ericsson C901", new BigDecimal(420), usd, 30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "xperiaxz",
                "Sony Xperia XZ", new BigDecimal(120), usd, 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "nokia3310",
                "Nokia 3310", new BigDecimal(70), usd, 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "palmp",
                "Palm Pixi", new BigDecimal(170), usd, 30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "simc56",
                "Siemens C56", new BigDecimal(70), usd, 20,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "simc61",
                "Siemens C61", new BigDecimal(80), usd, 30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        this.productList.add(new Product(Integer.toUnsignedLong(this.productList.size()+1), "simsxg75",
                "Siemens SXG75", new BigDecimal(150), usd, 40,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));

    }

    @Override
    public Product getProduct(Long id) {
        if(productList!=null){
            return productList.stream().filter(product->product.getId()==id).findFirst().get();
        }
        return null;
    }

    @Override
    public List<Product> findProducts() {
        if(productList==null){
            return null;
        }
        return productList.stream().filter(product -> (product.getStock()>0)&&(product.getPrice()!=null))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void  save(Product product) {
        if((product.getStock()>0)&&(product.getPrice()!=null)){
            productList.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        if(productList==null){
            return;
        }
        for(int i = 0; i < productList.size(); i++){
            if(productList.get(i).getId() == id){
               productList.remove(i);
            }
        }
    }

    @Override
    public String toString() {
        return "ArrayListProductDao{" +
                "productList=" + productList +
                '}';
    }
}
