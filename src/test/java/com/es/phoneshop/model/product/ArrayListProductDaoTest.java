package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ArrayListProductDao testArrayListProductDao;
    private static final Currency USD = Currency.getInstance("USD");
    //private static final Product normalProduct = new Product(1L, "sgs", "Samsung Galaxy S II", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

    @Before
    public void setup() {
        testArrayListProductDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void testFindNormalProducts() {
        testArrayListProductDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), USD, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testArrayListProductDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(100), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        assertTrue(testArrayListProductDao.findProducts().size() == 2);
    }

    @Test
    public void testDeleteNullPriceProducts() {
        testArrayListProductDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", null, USD, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testArrayListProductDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", null, USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        assertTrue(testArrayListProductDao.findProducts().size() == 0);
    }

    @Test
    public void testDeleteWrongStockProducts() {
        testArrayListProductDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), USD, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testArrayListProductDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(200), USD, -5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        assertTrue(testArrayListProductDao.findProducts().size() == 0);
    }


}
