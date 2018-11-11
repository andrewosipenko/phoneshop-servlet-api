package com.es.phoneshop.model.product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private List<Product> products = null;

    private Currency usd = Currency.getInstance("USD");
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        products = new ArrayList<>();

        product1 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        product2 = new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        product3 = new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        product4 = new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        product5 = new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");

    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void findProductsNotEqualsTest(){
        productDao.save(product1);
        productDao.save(product2);
        productDao.save(product3);
        productDao.save(product4);

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        assertNotEquals(productDao.findProducts(), products);
    }

    @Test
    public void findProductsEqualsTest(){
        productDao.save(product1);
        productDao.save(product3);
        productDao.save(product4);
        productDao.save(product5);

        products.add(product1);
        products.add(product3);
        products.add(product4);
        products.add(product5);

        assertEquals(productDao.findProducts(), products);
    }

    @Test
    public void getProductByExistingIdTest(){
        Long id = product1.getId();
        productDao.save(product1);

        assertEquals(productDao.getProduct(id), product1);
    }

    @Test(expected = RuntimeException.class)
    public void getProductByNotExistingIdTest(){
        Long id = product2.getId();
        productDao.save(product1);

        productDao.getProduct(id);

    }


    @Test
    public void saveTest(){
        Long id = product1.getId();

        productDao.save(product1);
        assertEquals(productDao.getProduct(id), product1);
    }
    @Test(expected = RuntimeException.class)
    public void saveFailedTest(){
        Long id = product1.getId();

        productDao.save(product1);
        productDao.save(product1);

    }

    @Test(expected = RuntimeException.class)
    public void deleteByExistingIdTest(){

        productDao.save(product1);

        Long id = product1.getId();
        productDao.delete(id);

        productDao.getProduct(id);
    }

    @Test(expected = RuntimeException.class)
    public void deleteByNotExistingIdTest(){
        productDao.save(product1);

        Long id = product2.getId();
        productDao.delete(id);

    }

    @After
    public void destroyAll(){
        productDao = null;
        products = null;
    }
}
