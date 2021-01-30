package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

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
    public void testSaveProduct() throws ProductNotFoundException {
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        assertNotNull(product.getId());
        assertEquals(product, productDao.getProduct(product.getId()));
    }

    @Test
    public void testUpdateProduct() throws ProductNotFoundException {
        productDao.save((new Product(0L, "updated", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg")));
        Product product = productDao.getProduct(0L);
        assertEquals("updated", product.getCode());
    }

    @Test
    public void testSaveProductWithExistingUniqueId() throws ProductNotFoundException {
        long id = ((ArrayListProductDao) productDao).getMaxId() + 5L;
        Product product = new Product(id, "updated", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        assertEquals(product,productDao.getProduct(id));
    }
    
    @Test
    public void testSuccessfulDeleteProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.delete(2L);
        int sizeAfter = productDao.findProducts().size();
        assertEquals(sizeBefore, sizeAfter + 1);
    }

    @Test
    public void testUnsuccessfulDeleteProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.delete(-5L);
        int sizeAfter = productDao.findProducts().size();
        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    public void testGetProduct() throws ProductNotFoundException {
        Product product = productDao.getProduct(2L);
        assertNotNull(product);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testUnsuccessfulGetProduct() throws ProductNotFoundException {
        productDao.getProduct(-5L);
    }
}
