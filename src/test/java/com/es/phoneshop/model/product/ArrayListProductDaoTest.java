package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        ((ArrayListProductDao) productDao).deleteAll();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts(null,null,null).isEmpty());
    }

    @Test
    public void testNotEmptyList(){
        assertNotNull(productDao.findProducts(null,null,null));
        Currency usd = Currency.getInstance("USD");
        Product expected=new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");
        productDao.save(expected);
        assertFalse(productDao.findProducts(null,null,null).isEmpty());
    }

    @Test
    public void testGetProduct(){
        Currency usd = Currency.getInstance("USD");
        Product expected=new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");
        productDao.save(expected);
        Product actual = productDao.getProduct(expected.getId());
        assertEquals(expected,actual);
    }

    @Test
    public void testGetNotExistingProduct(){
        Product actual = productDao.getProduct(15L);
        assertNull(actual);
    }

    @Test
    public void testDeleteProduct(){
        Currency usd = Currency.getInstance("USD");
        Product product=new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        assertTrue(productDao.findProducts(null,null,null).contains(product));
        productDao.delete(product.getId());
        assertNull(productDao.getProduct(product.getId()));
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteNotExistingProduct() throws NoSuchElementException{
        Currency usd = Currency.getInstance("USD");
        Product product=new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.delete(product.getId());
    }

    @Test
    public void testSaveNullProduct(){
        Product product=new Product();
        productDao.save(product);
        assertTrue(productDao.findProducts(null,null,null).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveExistingProduct(){
        Currency usd = Currency.getInstance("USD");
        Product product=new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        productDao.save(product);
    }

    @Test
    public void testSaveProduct(){
        Currency usd = Currency.getInstance("USD");
        Product product=new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        List<Product> expected=new ArrayList<>();
        expected.add(product);
        assertArrayEquals(expected.toArray(),productDao.findProducts(null,null,null).toArray());
    }

}
