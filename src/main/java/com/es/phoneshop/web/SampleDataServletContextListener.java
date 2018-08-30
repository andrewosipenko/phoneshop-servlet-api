package com.es.phoneshop.web;

import com.es.phoneshop.model.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static com.es.phoneshop.model.ArrayListProductDAO.generateId;

public class SampleDataServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        String addSampleData = servletContextEvent.getServletContext().getInitParameter("addSampleData");
        if(!Boolean.valueOf(addSampleData)) return;

        ProductDAO productDAO = ArrayListProductDAO.getInstance();

        productDAO.save(new Product(generateId(), "code 1", "description 1", new BigDecimal(100),
                Currency.getInstance(Locale.UK), 100));
        productDAO.save(new Product(generateId(), "code 2", "description 2", new BigDecimal(200),
                Currency.getInstance(Locale.UK), 200));
        productDAO.save(new Product(generateId(), "code 3", "description 3", new BigDecimal(300),
                Currency.getInstance(Locale.UK), 300));
        productDAO.save(new Product(generateId(), "code 4", "description 4", new BigDecimal(400),
                Currency.getInstance(Locale.UK), 400));
        productDAO.save(new Product(generateId(), "code 5", "description 5", new BigDecimal(500),
                Currency.getInstance(Locale.UK), 500));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
