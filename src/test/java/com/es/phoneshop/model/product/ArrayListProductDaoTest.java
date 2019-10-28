package com.es.phoneshop.model.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Product product, newProduct;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();

        Currency usd = Currency.getInstance("USD");
        product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        newProduct = new Product(14L, "iphone11pro", "Apple iPhone 11Pro", new BigDecimal(2000), usd, 1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
    }

    @Test
    public void testGetProductBadID() {
        try {
            productDao.getProduct(-1L);
            Assert.fail("Expected NoSuchElementException");
        }
        catch (NoSuchElementException thrown){
            Assert.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    public void testGetProductEqualsID() {
        assertEquals(productDao.getProduct(product.getId()).getId(), product.getId());
    }

    @Test
    public void testGetProductEquals() {
        assertEquals(productDao.getProduct(product.getId()), product);
    }

    @Test
    public void testGetProductNotNull() {
        assertNotNull(productDao.getProduct(product.getId()));
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsWithoutStockOrPrice() {
        assertTrue(productDao.findProducts().stream()
                .noneMatch(product -> product.getStock() <= 0 ||
                product.getPrice().compareTo(BigDecimal.ZERO) <= 0));
    }

    @Test
    public void testFindProductsNotNull() {
        assertNotNull(productDao.findProducts());
    }

    @Test
    public void testSaveBadProduct() {
        int oldSize = ((ArrayListProductDao)productDao).getProductList().size();
        try {
            productDao.save(product);
        }
        catch (Exception thrown){
            Assert.assertNotEquals("", thrown.getMessage());
        }
        assertEquals(((ArrayListProductDao)productDao).getProductList().size(), oldSize);
    }

    @Test
    public void testSave() throws Exception {
        int oldSize = ((ArrayListProductDao)productDao).getProductList().size();
        productDao.save(newProduct);
        assertEquals(((ArrayListProductDao)productDao).getProductList().size(), oldSize + 1);
    }

    @Test
    public void testDeleteNonexistentProduct() {
        int oldSize = ((ArrayListProductDao)productDao).getProductList().size();
        try {
            productDao.delete(-1L);
        }
        catch (NoSuchElementException thrown){
            Assert.assertNotEquals("", thrown.getMessage());
        }
        assertEquals(((ArrayListProductDao)productDao).getProductList().size(), oldSize);
    }

    @Test
    public void testDelete() {
        int oldSize = ((ArrayListProductDao)productDao).getProductList().size();
        productDao.delete(product.getId());
        assertEquals(((ArrayListProductDao)productDao).getProductList().size(), oldSize - 1);
    }
}