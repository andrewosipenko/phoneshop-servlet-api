package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private static final Long NOT_EXISTING_ID = 100L;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProducts() {
        List<Product> products = productDao.findProducts();

        assertTrue(products.stream()
                .allMatch(product -> product.getPrice() != null && product.getStock() > 0));
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProduct() {
        Product result = productDao.getProduct(2L);
        String productCode = "sgs2";

        assertEquals(productCode, result.getCode());
    }

    @Test
    public void testGetProductNotNull() {
        Product result = productDao.getProduct(2L);

        assertNotNull(result);
    }

    @Test
    public void testSave() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        assertEquals(product, productDao.getProduct(product.getId()));
    }

    @Test
    public void testUpdate() {
        Currency usd = Currency.getInstance("USD");
        List<Product> products = productDao.findProducts();
        Product product = new Product(1L, "sgs", "Apple iPhone 6", new BigDecimal(1200), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        int productIndex = 0;

        products.set(productIndex, product);

        assertEquals(products.get(productIndex), product);
    }

    @Test
    public void testDelete() {
        int size = productDao.findProducts().size();

        productDao.delete(1L);

        assertEquals(size - 1, productDao.findProducts().size());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testExceptionForNotFoundProductById() {
        productDao.getProduct(NOT_EXISTING_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionForNullProductParameter() {
        productDao.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionForNullIdParameter() {
        productDao.getProduct(null);

    }
}
