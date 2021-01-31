package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Currency usd;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        usd = Currency.getInstance("USD");
    }

    @Test
    public void testFindProducts() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        assertNotNull(product.getId());
        assertEquals(product, productDao.getProduct(product.getId()));
    }

    @Test
    public void testUpdateProduct() {
        Product product = productDao.getProduct(0L);
        assertEquals("sgs", product.getCode());
        productDao.save((new Product(0L, "updated", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg")));
        product = productDao.getProduct(0L);
        assertEquals("updated", product.getCode());
    }

    @Test(expected = RuntimeException.class)
    public void testTrySaveNull() {
        productDao.save(null);
    }

    @Test
    public void testSaveProductWithExistingUniqueId() {
        long id = ((ArrayListProductDao) productDao).getMaxId() + 5L;
        Product product = new Product(id, "updated", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        assertEquals(product, productDao.getProduct(id));
    }

    @Test(expected = NoSuchElementException.class)
    public void testSuccessfulDeleteProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.delete(2L);
        int sizeAfter = productDao.findProducts().size();
        assertEquals(sizeBefore, sizeAfter + 1);
        productDao.getProduct(2L);
    }

    @Test
    public void testUnsuccessfulDeleteProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.delete(-5L);
        int sizeAfter = productDao.findProducts().size();
        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    public void testGetProduct() {
        Product product = productDao.getProduct(2L);
        assertNotNull(product);
    }

    @Test(expected = NoSuchElementException.class)
    public void testUnsuccessfulGetProduct() {
        productDao.getProduct(-5L);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetProductWithZeroStock() {
        Product product = new Product("updated", "Samsung Galaxy S", new BigDecimal(100), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        productDao.getProduct(product.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetProductWithNullPrice() {
        Product product = new Product("updated", "Samsung Galaxy S", null, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        productDao.getProduct(product.getId());
    }
}
