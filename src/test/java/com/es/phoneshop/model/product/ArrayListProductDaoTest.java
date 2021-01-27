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
    public void setup() throws ProductNotFoundException {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testSaveNewProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product( "test-product", "TEST", new BigDecimal(10), usd, 15, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productDao.save(product);

        assertNotNull(product.getId());
        Product result = productDao.getProduct(product.getId());
        assertNotNull(result);
        assertEquals("test-product",result.getCode());
    }

    @Test
    public void testFindProductWithZeroStock() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product( "test-product", "TEST", new BigDecimal(10), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productDao.save(product);
        assertNull(product.getId());
    }

    @Test
    public void testFindProductWithNullPrice() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product( "test-product", "TEST", null, usd, 190, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productDao.save(product);
        assertNull(product.getId());
    }

    @Test
    public void testReplaceProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product( 2L,"test-product-for-replace", "TEST", new BigDecimal(10), usd, 15, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        productDao.save(product);

        assertNotNull(product.getId());
        Product result = productDao.getProduct(product.getId());
        assertNotNull(result);
        assertEquals("test-product-for-replace",result.getCode());
    }
}
