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

    @Before
    public void setup() {
        testArrayListProductDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void testProductSearch(){
        testArrayListProductDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), USD, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testArrayListProductDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(100), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        testArrayListProductDao.save(new Product(3L, "sony", "Sony", new BigDecimal(100), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        assertTrue(testArrayListProductDao.findProducts("Samsung",null,null).size() == 2);

    }

    @Test
    public void testProductSortingByDescription(){
        testArrayListProductDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), USD, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testArrayListProductDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(100), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        testArrayListProductDao.save(new Product(3L, "apple", "Apple", new BigDecimal(100), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        assertTrue(testArrayListProductDao.findProducts(null,"description","asc").get(0).getCode().equals("apple"));

    }

    @Test
    public void testProductSortingByPrice(){
        testArrayListProductDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(100), USD, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testArrayListProductDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(200), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        testArrayListProductDao.save(new Product(3L, "apple", "Apple", new BigDecimal(50), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        assertTrue(testArrayListProductDao.findProducts(null,"price","desc").get(0).getCode().equals("sgs3"));

    }
    @Test
    public void testFindNormalProducts() {
        testArrayListProductDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), USD, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testArrayListProductDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(100), USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        assertTrue(testArrayListProductDao.findProducts(null,null,null).size() == 2);
    }

    @Test
    public void testDeleteNullPriceProducts() {
        testArrayListProductDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", null, USD, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testArrayListProductDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", null, USD, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        assertTrue(testArrayListProductDao.findProducts(null,null,null).size() == 0);
    }

    @Test
    public void testDeleteWrongStockProducts() {
        testArrayListProductDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), USD, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testArrayListProductDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(200), USD, -5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        assertTrue(testArrayListProductDao.findProducts(null,null,null).size() == 0);
    }


}