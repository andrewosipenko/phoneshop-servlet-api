package com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.Currency;

public class SampleDataServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ProductDao productDao = ArrayListProductDao.getInstance();
        productDao.save(new Product(Product.generateId(), "1", "iPhone", new BigDecimal(10.0), Currency.getInstance("USD"), 2));
        productDao.save(new Product(Product.generateId(),"2", "iPhone5", new BigDecimal(0), Currency.getInstance("USD"), 1));
        productDao.save(new Product(Product.generateId(),"3", "iPhoneX", new BigDecimal(100.0), Currency.getInstance("USD"), 1));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
