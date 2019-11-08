package com.es.phoneshop.sampledata;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.*;

public class ProductSampleDataListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ProductDao productDao = ArrayListProductDao.getInstance();

        if("true".equals(servletContextEvent.getServletContext().getInitParameter("dataListenerRegulator"))) {
            Currency usd = Currency.getInstance("USD");
            Map<Date, BigDecimal> prices = new LinkedHashMap<>();
            Calendar date1 = new GregorianCalendar(2011, 10, 10);
            Calendar date2 = new GregorianCalendar(2015, 10, 10);
            Calendar date3 = new GregorianCalendar(2019, 10, 10);
            prices.put(date1.getTime(), new BigDecimal(300));
            prices.put(date2.getTime(), new BigDecimal(200));
            prices.put(date3.getTime(), new BigDecimal(100));
            productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", prices, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(400));
            prices.put(date2.getTime(), new BigDecimal(300));
            prices.put(date3.getTime(), new BigDecimal(200));
            productDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", prices, usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(500));
            prices.put(date2.getTime(), new BigDecimal(400));
            prices.put(date3.getTime(), new BigDecimal(300));
            productDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", prices, usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(400));
            prices.put(date2.getTime(), new BigDecimal(300));
            prices.put(date3.getTime(), new BigDecimal(200));
            productDao.save(new Product(4L, "iphone", "Apple iPhone", prices, usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(1200));
            prices.put(date2.getTime(), new BigDecimal(1100));
            prices.put(date3.getTime(), new BigDecimal(1000));
            productDao.save(new Product(5L, "iphone6", "Apple iPhone 6", prices, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(1200));
            prices.put(date2.getTime(), new BigDecimal(1100));
            prices.put(date3.getTime(), new BigDecimal(1000));
            productDao.save(new Product(14L, "iphone7", "Apple iPhone 7", prices, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(500));
            prices.put(date2.getTime(), new BigDecimal(400));
            prices.put(date3.getTime(), new BigDecimal(320));
            productDao.save(new Product(6L, "htces4g", "HTC EVO Shift 4G", prices, usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(600));
            prices.put(date2.getTime(), new BigDecimal(500));
            prices.put(date3.getTime(), new BigDecimal(420));
            productDao.save(new Product(7L, "sec901", "Sony Ericsson C901", prices, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(300));
            prices.put(date2.getTime(), new BigDecimal(200));
            prices.put(date3.getTime(), new BigDecimal(120));
            productDao.save(new Product(8L, "xperiaxz", "Sony Xperia XZ", prices, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(200));
            prices.put(date2.getTime(), new BigDecimal(100));
            prices.put(date3.getTime(), new BigDecimal(70));
            productDao.save(new Product(9L, "nokia3310", "Nokia 3310", prices, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(300));
            prices.put(date2.getTime(), new BigDecimal(200));
            prices.put(date3.getTime(), new BigDecimal(170));
            productDao.save(new Product(10L, "palmp", "Palm Pixi", prices, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(200));
            prices.put(date2.getTime(), new BigDecimal(100));
            prices.put(date1.getTime(), new BigDecimal(70));
            productDao.save(new Product(11L, "simc56", "Siemens C56", prices, usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(200));
            prices.put(date2.getTime(), new BigDecimal(100));
            prices.put(date3.getTime(), new BigDecimal(80));
            productDao.save(new Product(12L, "simc61", "Siemens C61", prices, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
            prices.clear();
            prices.put(date1.getTime(), new BigDecimal(300));
            prices.put(date2.getTime(), new BigDecimal(200));
            prices.put(date3.getTime(), new BigDecimal(150));
            productDao.save(new Product(13L, "simsxg75", "Siemens SXG75", prices, usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
