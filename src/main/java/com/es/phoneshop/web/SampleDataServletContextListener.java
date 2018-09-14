package com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class SampleDataServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if(sce.getServletContext().getInitParameter("ServletContextListenerIsTurnedOn").equals("true")) {
            ProductDao productDao = ArrayListProductDao.getInstance();
            Product product;
            for (int i = 0; i < 7; i++) {
                product = new Product();
                productDao.generateID(product);
                product.setCode(i + "");
                product.setDescription("description" + i);
                product.setPrice(new BigDecimal(i));
                product.setStock(i);
                product.setCurrency(Currency.getInstance(Locale.US));
                productDao.save(product);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContextListener destroyed");
    }
}
