package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsWithIncorrectConfigurationStock() {
        Product testProduct = new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, -10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.saveProduct(testProduct);
        List<Product> productList = productDao.findProducts();
        if (productList.contains(testProduct)) {
            fail("Must not be testProduct in productList");
        }
    }

    @Test
    public void testFindProductsWithIncorrectConfigurationPrice() {
        Product testProduct = new Product(0L, "sgs", "Samsung Galaxy S", null, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.saveProduct(testProduct);
        List<Product> productList = productDao.findProducts();
        if (productList.contains(testProduct)) {
            fail("Must not be testProduct in productList");
        }
    }

    @Test
    public void testGetProduct() {
        Product testProduct = new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        assertEquals(testProduct, productDao.getProduct(0L));

    }

    @Test
    public void testDeleteProductThatExist() {
        long id = 3L;
        productDao.deleteProduct(id);
        try {
            productDao.getProduct(id);
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testDeleteProductThatNotExist() {
        long id = -1L;
        try {
            productDao.deleteProduct(id);
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testSaveProductWithId() {
        Product testProduct = new Product(3L, "testProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.saveProduct(testProduct);
        assertEquals(testProduct, productDao.getProduct(3L));
    }

    @Test
    public void testSaveProductWithoutId() {
        Product testProduct = new Product("testProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.saveProduct(testProduct);
        assertEquals(testProduct, productDao.getProduct(testProduct.getId()));
    }

    @Test
    public void testSaveProductWithIdOutOfRange() {
        Product testProduct = new Product(-1L, "testProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.saveProduct(testProduct);
        assertEquals(testProduct, productDao.getProduct(testProduct.getId()));
    }
}
