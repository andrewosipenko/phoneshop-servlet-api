package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DemoDataServletContextListener implements ServletContextListener {
    private final ProductDao productDao;

    public DemoDataServletContextListener() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        boolean insertDemoData =
                Boolean.parseBoolean(servletContextEvent.getServletContext().getInitParameter("insertDemoData"));
        if (insertDemoData) {
            getSampleProducts().forEach(productDao::save);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public List<Product> getSampleProducts() {
        List<Product> products = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        products.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "/Samsung/Samsung%20Galaxy%20S.jpg"));
        products.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        products.add(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        products.add(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "/Apple/Apple%20iPhone.jpg"));
        products.add(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "/Apple/Apple%20iPhone%206.jpg"));
        products.add(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "/HTC/HTC%20EVO%20Shift%204G.jpg"));
        products.add(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "/Sony/Sony%20Ericsson%20C901.jpg"));
        products.add(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "/Sony/Sony%20Xperia%20XZ.jpg"));
        products.add(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "/Nokia/Nokia%203310.jpg"));
        products.add(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "/Palm/Palm%20Pixi.jpg"));
        products.add(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "/Siemens/Siemens%20C56.jpg"));
        products.add(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "/Siemens/Siemens%20C61.jpg"));
        products.add(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "/Siemens/Siemens%20SXG75.jpg"));
        return products;
    }
}
