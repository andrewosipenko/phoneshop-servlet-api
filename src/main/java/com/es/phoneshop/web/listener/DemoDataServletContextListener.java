package com.es.phoneshop.web.listener;

import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.entity.PriceHistory;
import com.es.phoneshop.model.product.entity.Product;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class DemoDataServletContextListener implements ServletContextListener {
    private ProductDao productDao;

    public DemoDataServletContextListener() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (Boolean.parseBoolean(servletContextEvent.getServletContext().getInitParameter("insertDemoData"))) {
            setSampleProducts();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void setSampleProducts() {
        Currency usd = Currency.getInstance("USD");

        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", createSamplePriceHistory(new PriceHistory("11 Feb 2003", 145L), new PriceHistory("28 Jun 2022", 100L))));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg", createSamplePriceHistory(new PriceHistory("12 Mar 2019", 200L))));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", createSamplePriceHistory(new PriceHistory("12 Mar 2019", 300L))));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg", createSamplePriceHistory(new PriceHistory("08 Aug 2019", 800L), new PriceHistory("04 Feb 2020", 1000L), new PriceHistory("12 Apr 2021", 200L))));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg", createSamplePriceHistory(new PriceHistory("12 Mar 2019", 800L), new PriceHistory("28 Jan 2018", 400L), new PriceHistory("11 May 2020", 1000L))));
        productDao.save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg", createSamplePriceHistory(new PriceHistory("12 Mar 2019", 800L), new PriceHistory("04 Oct 2022", 320L))));
        productDao.save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg", createSamplePriceHistory(new PriceHistory("12 Mar 2019", 420L))));
        productDao.save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg", createSamplePriceHistory(new PriceHistory("12 Mar 2018", 100L), new PriceHistory("12 Apr 2023", 120L))));
        productDao.save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg", createSamplePriceHistory(new PriceHistory("05 Feb 2017", 80L), new PriceHistory("23 Aug 2020", 70L))));
        productDao.save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg", createSamplePriceHistory(new PriceHistory("12 Mar 2019", 800L), new PriceHistory("12 Mar 2020", 1000L), new PriceHistory("12 Mar 2021", 170L))));
        productDao.save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg", createSamplePriceHistory(new PriceHistory("30 Sep 2022", 70L))));
        productDao.save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg", createSamplePriceHistory(new PriceHistory("12 Mar 2019", 800L), new PriceHistory("02 Mar 2020", 100L), new PriceHistory("03 Oct 2021", 1L), new PriceHistory("26 Jan 2022", 80L))));
        productDao.save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg", createSamplePriceHistory(new PriceHistory("14 Nov 2016", 100L), new PriceHistory("10 Jan 2017", 150L))));
    }

    private List<PriceHistory> createSamplePriceHistory(PriceHistory... priceHistories) {
        return List.of(priceHistories);
    }
}
