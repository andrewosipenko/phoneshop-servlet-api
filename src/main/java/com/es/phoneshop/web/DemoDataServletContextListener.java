package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.ProductService;
import com.es.phoneshop.service.ProductServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.math.BigDecimal;
import java.util.Currency;

public class DemoDataServletContextListener implements ServletContextListener {

    private ProductService productService;

    public DemoDataServletContextListener() {
        this.productService = ProductServiceImpl.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        boolean insertDemoData = Boolean.parseBoolean(servletContext.getInitParameter("insertDemoData"));
        if (insertDemoData) {
            saveSampleProducts();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContextListener.super.contextDestroyed(event);
    }

    private void saveSampleProducts() {
        Currency usd = Currency.getInstance("USD");
        Product product;
        product = (new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        product.setPrice(new BigDecimal(110));
        product.setPrice(new BigDecimal(120));
        productService.save(product);
        product = (new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        product.setPrice(new BigDecimal(210));
        productService.save(product);
        product = (new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        product.setPrice(new BigDecimal(290));
        productService.save(product);
        product = (new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        product.setPrice(new BigDecimal(210));
        productService.save(product);
        product = (new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        product.setPrice(new BigDecimal(950));
        productService.save(product);
        product = (new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        product.setPrice(new BigDecimal(300));
        product.setPrice(new BigDecimal(350));
        productService.save(product);
        product = (new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        product.setPrice(new BigDecimal(400));
        product.setPrice(new BigDecimal(450));
        productService.save(product);
        product = (new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        product.setPrice(new BigDecimal(100));
        product.setPrice(new BigDecimal(90));
        productService.save(product);
        product = (new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        product.setPrice(new BigDecimal(80));
        product.setPrice(new BigDecimal(70));
        productService.save(product);
        product = (new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        product.setPrice(new BigDecimal(180));
        product.setPrice(new BigDecimal(190));
        productService.save(product);
        product = (new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        product.setPrice(new BigDecimal(100));
        product.setPrice(new BigDecimal(80));
        productService.save(product);
        product = (new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        product.setPrice(new BigDecimal(150));
        product.setPrice(new BigDecimal(10));
        productService.save(product);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
