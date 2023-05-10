package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.PriceHistory;
import com.es.phoneshop.model.Product;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class ProductDemoDataServletContextListener implements ServletContextListener {
    private ProductDao productDao;
    private static final String DEMO_DATA = "demoData";

    public ProductDemoDataServletContextListener() {
        this.productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        boolean demoData = Boolean.valueOf(sce.getServletContext().getInitParameter(DEMO_DATA));
        if (demoData) {
            getSampleProducts().stream()
                    .forEach(product -> productDao.save(product));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }

    public List<Product> getSampleProducts() {
        List<Product> products = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        products.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        products.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        products.add(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        products.add(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        products.add(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        products.add(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        products.add(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        products.add(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        products.add(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        products.add(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        products.add(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        products.add(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        products.add(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
        formPriceHistory(products);
        return products;
    }

    public void formPriceHistory(List<Product> products) {
        Currency usd = Currency.getInstance("USD");
        products.get(0).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(100), usd));
        products.get(0).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,11), new BigDecimal(90), usd));
        products.get(0).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,20), new BigDecimal(80), usd));
        products.get(1).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(200), usd));
        products.get(1).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,1), new BigDecimal(180), usd));
        products.get(1).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,2), new BigDecimal(150), usd));
        products.get(2).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(300), usd));
        products.get(2).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,5), new BigDecimal(290), usd));
        products.get(2).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,25), new BigDecimal(286), usd));
        products.get(3).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(200), usd));
        products.get(3).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,11), new BigDecimal(90), usd));
        products.get(3).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,20), new BigDecimal(100), usd));
        products.get(4).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(1000), usd));
        products.get(4).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,16), new BigDecimal(900), usd));
        products.get(4).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,21), new BigDecimal(850), usd));
        products.get(5).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(320), usd));
        products.get(5).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,17), new BigDecimal(300), usd));
        products.get(5).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,22), new BigDecimal(298), usd));
        products.get(6).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(420), usd));
        products.get(6).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,11), new BigDecimal(90), usd));
        products.get(6).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,20), new BigDecimal(100), usd));
        products.get(7).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(120), usd));
        products.get(7).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,11), new BigDecimal(117), usd));
        products.get(7).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,13), new BigDecimal(110), usd));
        products.get(8).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(70), usd));
        products.get(8).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,11), new BigDecimal(69), usd));
        products.get(8).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,20), new BigDecimal(55), usd));
        products.get(9).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(170), usd));
        products.get(9).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,16), new BigDecimal(160), usd));
        products.get(9).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,26), new BigDecimal(157), usd));
        products.get(10).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(70), usd));
        products.get(10).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,11), new BigDecimal(67), usd));
        products.get(10).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,20), new BigDecimal(62), usd));
        products.get(11).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(80), usd));
        products.get(11).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,10), new BigDecimal(70), usd));
        products.get(11).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,20), new BigDecimal(65), usd));
        products.get(12).getHistories().add(new PriceHistory(LocalDate.now(), new BigDecimal(150), usd));
        products.get(12).getHistories().add(new PriceHistory(LocalDate.of(2023, 4,11), new BigDecimal(130), usd));
        products.get(12).getHistories().add(new PriceHistory(LocalDate.of(2023, 3,5), new BigDecimal(120), usd));
    }
}
