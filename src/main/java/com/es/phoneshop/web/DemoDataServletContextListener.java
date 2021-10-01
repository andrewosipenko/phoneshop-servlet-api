package com.es.phoneshop.web;

import com.es.phoneshop.model.product.productdao.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DemoDataServletContextListener implements ServletContextListener {
    private ProductDao productDao;

    public DemoDataServletContextListener() {
        this.productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        boolean isDemoDataEnable = Boolean.parseBoolean(sce.getServletContext().getInitParameter("enable"));
        if (isDemoDataEnable) {
            saveSampleProducts();
        }
    }

    private void saveSampleProducts() {
        Currency usd = Currency.getInstance("USD");
        PriceHistory time1 = new PriceHistory(LocalDate.of(2015, 3, 2), new BigDecimal(50));
        PriceHistory time2 = new PriceHistory(LocalDate.of(2017, 3, 2), new BigDecimal(100));
        List<PriceHistory> priceHistoryList = new ArrayList<>();
        priceHistoryList.add(time1);
        priceHistoryList.add(time2);
        productDao.saveProduct(new ProductBuilderImpl().setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("sgs2").setDescription("Samsung Galaxy S II").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(0).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("iphone6").setDescription("Apple iPhone 6").setPrice(new BigDecimal(1000)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("htces4g").setDescription("HTC EVO Shift 4G").setPrice(new BigDecimal(320)).setCurrency(usd).setStock(3).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("sec901").setDescription("Sony Ericsson C901").setPrice(new BigDecimal(420)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("xperiaxz").setDescription("Sony Xperia XZ").setPrice(new BigDecimal(120)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("nokia3310").setDescription("Nokia 3310").setPrice(new BigDecimal(70)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("palmp").setDescription("Palm Pixi").setPrice(new BigDecimal(170)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("simc56").setDescription("Siemens C56").setPrice(new BigDecimal(70)).setCurrency(usd).setStock(20).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("simc61").setDescription("Siemens C61").setPrice(new BigDecimal(80)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setCode("simsxg75").setDescription("Siemens SXG75").setPrice(new BigDecimal(150)).setCurrency(usd).setStock(40).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg").setPriceHistory(priceHistoryList).build());
    }
}