package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductWithZeroStock() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, -1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        assertTrue(product.getId() > 0);
        Product result = productDao.getProduct(product.getId());
        assertNotNull(result);
        assertEquals(result.getCode(), "test-product");
    }

    @Test
    public void testSaveProductWithExistingId() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, -1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        assertTrue(product.getId() > 0);
        Product product1 = new Product(product.getId(), "new-test-product", "Samsung Galaxy S", new BigDecimal(100), usd, -1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product1);
        Product result = productDao.getProduct(product.getId());
        assertNotNull(result);
        assertEquals(result.getCode(), "new-test-product");
    }

    @Test
    public void testDeleteProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, 1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        assertTrue(productDao.findProducts().contains(product));
        productDao.delete(product.getId());
        assertFalse(productDao.findProducts().contains(product));
    }

    @Test
    public void testDeleteProductIdChanging() throws ProductNotFoundException {
        assertNotNull(productDao.getProduct(2L));
        productDao.delete(2L);
        long idCounter = 1L;
        for (var product:
             productDao.findProducts()) {
            assertEquals((long) product.getId(), idCounter++);
        }
    }
}
