package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testDeleteProduct() {
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("sgs", "Xiaomi", new BigDecimal(100), usd, 100, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        assertTrue(productDao.findProducts().contains(p));
        productDao.delete(p.getId());
        assertFalse(productDao.findProducts().contains(p));
    }

    @Test
    public void testFindProducts() {
        assertFalse(productDao.findProducts().isEmpty());
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("sgs", "Xiaomi", new BigDecimal(100), usd, 100, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        assertTrue(productDao.findProducts().contains(p));
        p = new Product("sgs", "Xiaomi", new BigDecimal(100), usd, 0, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        assertFalse(productDao.findProducts().contains(p));
        p = new Product("sgs", "Xiaomi", null, usd, 10, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        assertFalse(productDao.findProducts().contains(p));
    }

    @Test
    public void testSaveProduct() {
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        Long id = p.getId();
        assertNotNull(id);
        assertEquals(productDao.getProduct(p.getId()), p);
        productDao.save(p);
        assertEquals(p.getId(), id);
    }
    @Test
    public void testGetProduct() {
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        assertEquals(productDao.getProduct(p.getId()), p);
    }
}
