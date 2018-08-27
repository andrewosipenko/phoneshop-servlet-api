package com.es.phoneshop.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao products = ArrayListProductDao.getInstance();

    @Before
    public void init() {
        products.save(new Product(1L, "a1", "descr1", new BigDecimal(162),
                Currency.getInstance("BYN"), 61));
    }

    @Test
    public void testGetInstance() {
        ProductDao productDao = ArrayListProductDao.getInstance();


        assertNotNull(productDao);
    }

    @Test
    public void testGetProduct() {
        Long id = products.getProduct(1L).getId();


        assertEquals(1L, id.longValue());
    }

    @Test
    public void testFindProducts() {
        boolean isListEmpty = products.findProducts().isEmpty();


        assertFalse(isListEmpty);
    }

    @Test
    public void testSave() {
        products.save(new Product(2L, "a2", "descr2", new BigDecimal(162),
                Currency.getInstance("BYN"), 61));


        Product product = products.getProduct(2L);


        assertNotNull(product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemove() {
        products.save(new Product(4L, "a4", "descr4", new BigDecimal(123),
                Currency.getInstance("BYN"), 16));


        products.remove(4L);


        products.getProduct(4L);
    }
}