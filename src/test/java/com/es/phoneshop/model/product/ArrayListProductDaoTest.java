package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
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
    public void testSaveNewProduct() {

        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);
        assertTrue(product.getId() > 0);
        Product result = productDao.getProduct(product.getId());
        assertNotNull(result);
        assertEquals("test", result.getCode());
    }

    @Test
    public void testUpdateProduct() {

        Currency usd = Currency.getInstance("USD");
        Product product = new Product(2L, "test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        assertEquals("sgs3", productDao.getProduct(2L).getCode());
        productDao.save(product);
        assertEquals(2L, (long) product.getId());

        Product result = productDao.getProduct(product.getId());
        assertEquals("test", result.getCode());
        assertEquals(12, productDao.findProducts().size());
    }

    @Test
    public void testDeleteProduct() {
        int sizeBefore = productDao.findProducts().size();
        productDao.delete(0L);
        assertNull(productDao.getProduct(0L));
        assertEquals(sizeBefore, productDao.findProducts().size() + 1);

    }

}
