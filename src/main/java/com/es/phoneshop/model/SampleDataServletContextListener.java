package com.es.phoneshop.model;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.Currency;


public class SampleDataServletContextListener implements ServletContextListener {

    private static long count = 1L;
    private synchronized long generateId(){
        return count++;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        if(!Boolean.valueOf(servletContextEvent.getServletContext().getInitParameter("SampleData"))){
            return;
        }
        ProductDao products = ArrayListProductDao.getInstance();
        products.save(new Product(generateId(),"17.1010405", "Xiaomi Pocophone F1 64GB", new BigDecimal(949.00), Currency.getInstance("BYN"), 61));
        products.save(new Product(generateId(),"17.1001233", "HTC U Play", new BigDecimal(749.00), Currency.getInstance("BYN"), 0));
        products.save(new Product(generateId(),"17.1010114", "Samsung Galaxy Note9 128GB", new BigDecimal(2339.00), Currency.getInstance("BYN"), 12));
        products.save(new Product(generateId(),"17.1008510", "Apple iPhone SE 32GB", new BigDecimal(799.00), Currency.getInstance("BYN"), 16));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
