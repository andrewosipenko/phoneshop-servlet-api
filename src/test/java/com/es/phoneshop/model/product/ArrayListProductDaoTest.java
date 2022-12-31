package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() throws ProductAlreadyExistsException {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testSaveNewProduct() throws ProductNotFoundException, ProductAlreadyExistsException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertTrue(product.getId() > 0);
        Product result = productDao.getProduct(Long.valueOf(product.getId()));
        assertNotNull(result);
        assertEquals("test", result.getCode());
    }

    @Test
    public void testSaveNewProductCorrectIdSet() throws ProductAlreadyExistsException {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 = new Product("test2", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product1);
        productDao.save(product2);

        assertEquals(1L, product2.getId() - product1.getId());
    }

    @Test(expected = ProductAlreadyExistsException.class)
    public void testSaveExistedProduct() throws ProductAlreadyExistsException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        productDao.save(product);
    }

    @Test
    public void testGetProductCorrectId() throws ProductNotFoundException, ProductAlreadyExistsException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertEquals(product, productDao.getProduct(product.getId()));
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductIncorrectId() throws ProductNotFoundException, ProductAlreadyExistsException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        productDao.getProduct(product.getId() + 1L);
    }

    @Test
    public void testFindProductsWithCorrectConditions() throws ProductAlreadyExistsException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        List<Product> products = new ArrayList<>();
        products.add(product);

        assertTrue(productDao.findProducts().contains(product));
    }
    @Test
    public void testFindProductsWithIncorrectPrice() throws ProductAlreadyExistsException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", null, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        assertFalse(productDao.findProducts().contains(product));
    }

    @Test
    public void testFindProductsWithIncorrectStock() throws ProductAlreadyExistsException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, -5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        assertFalse(productDao.findProducts().contains(product));
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNonExistedProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.delete(product.getId());
    }

    @Test
    public void testDeleteExistedProduct() throws ProductNotFoundException, ProductAlreadyExistsException {
        List<Product> productsBeforeDeleting = productDao.findProducts();

        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        productDao.delete(product.getId());
        List<Product> productsAfterDeleting = productDao.findProducts();

        assertTrue(productsBeforeDeleting.equals(productsAfterDeleting));
    }
}
