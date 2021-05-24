package com.es.phoneshop.model.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private  Currency usd;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        usd = Currency.getInstance("USD");
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }


    @Test
    public void testSaveNewProduct() {
        Product product = new Product( "test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertTrue(product.getId()>0);
        Product result = productDao.getProduct(product.getId());
        assertNotNull(result);
        assertEquals("test",result.getCode());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product( 1L,"test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        Product newProduct = new Product( 1L,"new-test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(newProduct);

        Product result = productDao.getProduct(1L);
        assertEquals("new-test",result.getCode());

    }

    @Test
    public void testFindProductById() {
        Product product = new Product( 1L,"test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        assertNotNull(productDao.getProduct(product.getId()));
    }

    @Test
    public void testFindProductWithNoSuchId() {
        assertNull(productDao.getProduct(0L));
    }

    @Test
    public void testFindProductWithNullId() {
        assertNull(productDao.getProduct(null));
    }


    @Test
    public void testDelete() {
        Product product = new Product( 1L,"test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        productDao.delete(1L);
        assertNull(productDao.getProduct(1L));

    }


}
