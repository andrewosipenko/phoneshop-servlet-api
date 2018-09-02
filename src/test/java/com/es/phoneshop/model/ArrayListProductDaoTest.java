package com.es.phoneshop.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao products = ArrayListProductDao.getInstance();

    private static final Long EXISTING_ID = 1L;

    @Before
    public void init() {
        products.save(new Product(EXISTING_ID, "a1", "descr1", new BigDecimal(162),
                Currency.getInstance("BYN"), 61));
    }

    @Test
    public void testGetInstance() {
        ProductDao productDao = ArrayListProductDao.getInstance ();

        assertNotNull(productDao);
    }

    @Test
    public void testGetProduct() {
        Long id = products.getProduct(EXISTING_ID).getId();

        assertEquals(EXISTING_ID.longValue(), id.longValue());
    }

    @Test
    public void testFindProducts() {
        boolean isListEmpty = products.findProducts().isEmpty();

        assertFalse(isListEmpty);
    }

    @Test
    public void testSave() {
        Long localId = 2L;
        products.save(new Product(localId, "a2", "descr2", new BigDecimal(162),
                Currency.getInstance("BYN"), 61));

        Product product = products.getProduct(localId);

        assertNotNull(product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemove() {
        final Long localId = 4L;
        products.save(new Product(localId, "a4", "descr4", new BigDecimal(123),
                Currency.getInstance("BYN"), 16));

        products.remove(localId);

        products.getProduct(localId);
    }
}