package com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Price;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DemodataServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//      Boolean DemodataIsEnabled = sce.getServletContext().getInitParameter("demodata").equals("enable");
        String demodata = sce.getServletContext().getInitParameter("demodata");
        if (demodata.equalsIgnoreCase("enable")) {
            ProductDao productDao = ArrayListProductDao.getInstance();
            productDao.saveAll(getSampleProducts());
        }
    }

    private List<Product> getSampleProducts() {
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");

        List<Price> pricesSamsungGalaxyS = new ArrayList<>();
        pricesSamsungGalaxyS.add(new Price(LocalDate.ofYearDay(2001, 18), new BigDecimal(250)));
        pricesSamsungGalaxyS.add(new Price(LocalDate.ofYearDay(2004, 138), new BigDecimal(230)));
        pricesSamsungGalaxyS.add(new Price(LocalDate.ofYearDay(2008, 18), new BigDecimal(200)));

        List<Price> pricesSamsungGalaxySII = new ArrayList<>();
        pricesSamsungGalaxySII.add(new Price(LocalDate.ofYearDay(2002, 18), new BigDecimal(300)));
        pricesSamsungGalaxySII.add(new Price(LocalDate.ofYearDay(2005, 158), new BigDecimal(230)));
        pricesSamsungGalaxySII.add(new Price(LocalDate.ofYearDay(2008, 52), new BigDecimal(220)));


        List<Price> pricesSamsungGalaxySIII = new ArrayList<>();
        pricesSamsungGalaxySIII.add(new Price(LocalDate.ofYearDay(2003, 18), new BigDecimal(300)));
        pricesSamsungGalaxySIII.add(new Price(LocalDate.ofYearDay(2008, 158), new BigDecimal(230)));
        pricesSamsungGalaxySIII.add(new Price(LocalDate.ofYearDay(2009, 52), new BigDecimal(220)));

        List<Price> pricesAppleIphone = new ArrayList<>();
        pricesAppleIphone.add(new Price(LocalDate.ofYearDay(2004, 18), new BigDecimal(300)));
        pricesAppleIphone.add(new Price(LocalDate.ofYearDay(2009, 158), new BigDecimal(230)));
        pricesAppleIphone.add(new Price(LocalDate.ofYearDay(2010, 52), new BigDecimal(200)));


        List<Price> pricesAppleIphoneVI = new ArrayList<>();
        pricesAppleIphoneVI.add(new Price(LocalDate.ofYearDay(2005, 18), new BigDecimal(1500)));
        pricesAppleIphoneVI.add(new Price(LocalDate.ofYearDay(2005, 158), new BigDecimal(1003)));
        pricesAppleIphoneVI.add(new Price(LocalDate.ofYearDay(2010, 52), new BigDecimal(1000)));

        List<Price> pricesHtcEvoShift4G = new ArrayList<>();
        pricesHtcEvoShift4G.add(new Price(LocalDate.ofYearDay(2005, 18), new BigDecimal(800)));
        pricesHtcEvoShift4G.add(new Price(LocalDate.ofYearDay(2005, 158), new BigDecimal(500)));
        pricesHtcEvoShift4G.add(new Price(LocalDate.ofYearDay(2010, 52), new BigDecimal(320)));

        List<Price> pricesSonyEricssonC901 = new ArrayList<>();
        pricesSonyEricssonC901.add(new Price(LocalDate.ofYearDay(2005, 18), new BigDecimal(800)));
        pricesSonyEricssonC901.add(new Price(LocalDate.ofYearDay(2005, 158), new BigDecimal(500)));
        pricesSonyEricssonC901.add(new Price(LocalDate.ofYearDay(2010, 52), new BigDecimal(420)));

        List<Price> pricesSonyXperiaXZ = new ArrayList<>();
        pricesSonyXperiaXZ.add(new Price(LocalDate.ofYearDay(2005, 18), new BigDecimal(800)));
        pricesSonyXperiaXZ.add(new Price(LocalDate.ofYearDay(2005, 158), new BigDecimal(500)));
        pricesSonyXperiaXZ.add(new Price(LocalDate.ofYearDay(2010, 52), new BigDecimal(120)));

        List<Price> pricesNokia3310 = new ArrayList<>();
        pricesNokia3310.add(new Price(LocalDate.ofYearDay(2005, 158), new BigDecimal(500)));
        pricesNokia3310.add(new Price(LocalDate.ofYearDay(2005, 18), new BigDecimal(800)));
        pricesNokia3310.add(new Price(LocalDate.ofYearDay(2010, 52), new BigDecimal(70)));

        List<Price> pricesPalmPixi = new ArrayList<>();
        pricesPalmPixi.add(new Price(LocalDate.ofYearDay(2005, 158), new BigDecimal(500)));
        pricesPalmPixi.add(new Price(LocalDate.ofYearDay(2005, 18), new BigDecimal(800)));
        pricesPalmPixi.add(new Price(LocalDate.ofYearDay(2010, 52), new BigDecimal(170)));

        List<Price> pricesSiemensC56 = new ArrayList<>();
        pricesSiemensC56.add(new Price(LocalDate.ofYearDay(2005, 158), new BigDecimal(500)));
        pricesSiemensC56.add(new Price(LocalDate.ofYearDay(2005, 18), new BigDecimal(800)));
        pricesSiemensC56.add(new Price(LocalDate.ofYearDay(2010, 52), new BigDecimal(70)));

        List<Price> pricesSiemensC61 = new ArrayList<>();
        pricesSiemensC61.add(new Price(LocalDate.ofYearDay(2005, 158), new BigDecimal(500)));
        pricesSiemensC61.add(new Price(LocalDate.ofYearDay(2005, 18), new BigDecimal(800)));
        pricesSiemensC61.add(new Price(LocalDate.ofYearDay(2010, 52), new BigDecimal(80)));

        List<Price> pricesSiemensSxg75 = new ArrayList<>();
        pricesSiemensSxg75.add(new Price(LocalDate.ofYearDay(2005, 158), new BigDecimal(500)));
        pricesSiemensSxg75.add(new Price(LocalDate.ofYearDay(2005, 18), new BigDecimal(800)));
        pricesSiemensSxg75.add(new Price(LocalDate.ofYearDay(2010, 52), new BigDecimal(150)));

        result.add(new Product(1L, "sgs", "Samsung Galaxy S", pricesSamsungGalaxyS, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        result.add(new Product(2L, "sgs2", "Samsung Galaxy S II", pricesSamsungGalaxySII, usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        result.add(new Product(3L, "sgs3", "Samsung Galaxy S III", pricesSamsungGalaxySIII, usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        result.add(new Product(4L, "iphone", "Apple iPhone", pricesAppleIphone, usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        result.add(new Product(5L, "iphone6", "Apple iPhone 6", pricesAppleIphoneVI, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        result.add(new Product(6L, "htces4g", "HTC EVO Shift 4G", pricesHtcEvoShift4G, usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        result.add(new Product(7L, "sec901", "Sony Ericsson C901", pricesSonyEricssonC901, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        result.add(new Product(8L, "xperiaxz", "Sony Xperia XZ", pricesSonyXperiaXZ, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        result.add(new Product(9L, "nokia3310", "Nokia 3310", pricesNokia3310, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        result.add(new Product(10L, "palmp", "Palm Pixi", pricesPalmPixi, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        result.add(new Product(11L, "simc56", "Siemens C56", pricesSiemensC56, usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        result.add(new Product(12L, "simc61", "Siemens C61", pricesSiemensC61, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        result.add(new Product(13L, "simsxg75", "Siemens SXG75", pricesSiemensSxg75, usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
        return result;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

