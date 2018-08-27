package com.es.phoneshop.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao products = ArrayListProductDao.getInstance();

    @Before
    public void init(){
        products.save(new Product(1L,"a1", "descr1", new BigDecimal(162),
                Currency.getInstance("BYN"), 61));
    }

    @Test
    public void getInstance(){
        assertNotNull(ArrayListProductDao.getInstance());
    }

    @Test
    public void getProduct() {
        assertEquals(1L, products.getProduct(1L).getId().longValue());
    }

    @Test
    public void findProducts() {
        assertFalse(products.findProducts().isEmpty());
    }

    @Test
    public void save() {
        products.save(new Product(2L,"a2", "descr2", new BigDecimal(162),
                Currency.getInstance("BYN"), 61));
        assertNotNull(products.getProduct(2L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void remove() {
        products.save(new Product(4L,"a4", "descr4", new BigDecimal(123),
                Currency.getInstance("BYN"), 16));
        products.remove(4L);
        products.getProduct(4L);
    }
}