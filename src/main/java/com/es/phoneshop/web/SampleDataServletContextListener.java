package com.es.phoneshop.web;

import com.es.phoneshop.model.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class SampleDataServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ProductDao dao = ArrayListProductDao.getInstance();

        if(!Boolean.valueOf(servletContextEvent.getServletContext().getInitParameter("addSampleData")))
            return;

        dao.save(new Product(1L, "A1B", "desc1", new BigDecimal("123.3"),
                Currency.getInstance(Locale.UK), 1));

        dao.save(new Product(2L, "A2B", "desc2", new BigDecimal("101.1"),
                Currency.getInstance(Locale.CANADA), 123));

        dao.save(new Product(3L, "A3B", "desc3", new BigDecimal("12323.3"),
                Currency.getInstance(Locale.KOREA), 123123));

        dao.save(new Product(4L, "A4B", "desc4", new BigDecimal("112323.3"),
                Currency.getInstance(Locale.JAPAN), 1321223));

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
