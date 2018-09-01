package com.es.phoneshop.model;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;

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
        products.save(new Product(generateId(),"a1", "descr1", new BigDecimal(162), Currency.getInstance("BYN"), 61));
        products.save(new Product(generateId(),"a2", "descr2", new BigDecimal(112), Currency.getInstance("BYN"), 0));
        products.save(new Product(generateId(),"a3", "descr3", new BigDecimal(2), Currency.getInstance("BYN"), 12));
        products.save(new Product(generateId(),"a4", "descr4", new BigDecimal(123), Currency.getInstance("BYN"), 16));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
