package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
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
    public void testFindProducts() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProduct() throws ProductNotFoundException {
        assertNotNull(productDao.getProduct(3L));
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductException() throws ProductNotFoundException {
        assertNotNull(productDao.getProduct(null));
    }

    @Test
    public void testSaveProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-code", "test-description", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);
        assertTrue(product.getId() > 0);
        Product result = productDao.getProduct(Long.valueOf(product.getId()));
        assertNotNull(result);
        assertEquals("test-code", result.getCode());
    }

    @Test
    public void testSaveAndUpdateProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);
        Product productUpd = new Product("test-codeUpdate", "test-description", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productUpd.setId(product.getId());

        productDao.save(productUpd);

        assertEquals("test-codeUpdate", productDao.getProduct(product.getId()).getCode());
    }

    @Test (expected = ProductNotFoundException.class)
    public void testDeleteProduct() throws ProductNotFoundException {
        assertNotNull(productDao.getProduct(1L));

        productDao.delete(1L);
        assertNull(productDao.getProduct(1L));
    }
}
