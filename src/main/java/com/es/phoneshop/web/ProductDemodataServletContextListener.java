package com.es.phoneshop.web;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductDemodataServletContextListener implements ServletContextListener {
    private static final Currency USD = Currency.getInstance("USD");

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), USD, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg",
                history(new BigDecimal(100))));
        productDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), USD, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg",
                history(new BigDecimal(200))));
        productDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg",
                history(new BigDecimal(300))));
        productDao.save(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), USD, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                history(new BigDecimal(200))));
        productDao.save(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg",
                history(new BigDecimal(1000))));
        productDao.save(new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), USD, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg",
                history(new BigDecimal(320))));
        productDao.save(new Product(7L, "sec901", "Sony Ericsson C901", new BigDecimal(420), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg",
                history(new BigDecimal(420))));
        productDao.save(new Product(8L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), USD, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg",
                history(new BigDecimal(120))));
        productDao.save(new Product(9L, "nokia3310", "Nokia 3310", new BigDecimal(70), USD, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg",
                history(new BigDecimal(70))));
        productDao.save(new Product(10L, "palmp", "Palm Pixi", new BigDecimal(170), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg",
                history(new BigDecimal(170))));
        productDao.save(new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), USD, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg",
                history(new BigDecimal(70))));
        productDao.save(new Product(12L, "simc61", "Siemens C61", new BigDecimal(80), USD, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg",
                history(new BigDecimal(80))));
        productDao.save(new Product(13L, "simsxg75", "Siemens SXG75", new BigDecimal(150), USD, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg",
                history(new BigDecimal(150))));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private Map<BigDecimal, PriceHistory> history(BigDecimal price) {
        Map<BigDecimal, PriceHistory> historyMap = new LinkedHashMap<>();
        BigDecimal price1 = price.multiply(BigDecimal.valueOf(0.5));
        BigDecimal price2 = price.multiply(BigDecimal.valueOf(1.5));
        BigDecimal price3 = price.multiply(BigDecimal.valueOf(2.0));
        LocalDate date1 = LocalDate.of(2018, 9, 6);
        LocalDate date2 = LocalDate.of(2019, 1, 9);
        LocalDate date3 = LocalDate.of(2019, 3, 22);
        LocalDate date4 = LocalDate.of(2019, 6, 30);
        historyMap.put(price1, new PriceHistory(date1, price1, USD));
        historyMap.put(price2, new PriceHistory(date2, price2, USD));
        historyMap.put(price3, new PriceHistory(date3, price3, USD));
        historyMap.put(price, new PriceHistory(date4, price, USD));
        return historyMap;
    }
}