package com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;

public class SampleDataServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if(sce.getServletContext().getInitParameter("ServletContextListenerIsTurnedOn").equals("true")) {
            System.out.println("ServletContextListener started");
            ProductDao productDao = ArrayListProductDao.getInstance();
            Product product;
            for (int i = 0; i < 7; i++) {
                product = new Product();
                ProductIDGenerator.generateID(product);
                product.setCode(i + "");
                product.setDescription("description" + i);
                product.setPrice(new BigDecimal(i));
                product.setStock(i);
                productDao.save(product);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContextListener destroyed");
    }
}
