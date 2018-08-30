package com.es.phoneshop.model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {

    private ArrayListProductDao productDao;
    private long productID;

    @Before
    public void setTectProducts() {
        productDao = ArrayListProductDao.getInstance();
        productID = Product.generateId();
        productDao.save(new Product(productID, "4", "iPhone7", new BigDecimal(1000.0), Currency.getInstance("USD"), 10));
    }


    @Test
    public void testSave() {
        Long id = Product.generateId();
        assertNull(productDao.getProduct(id));
        productDao.save(new Product(id, "5", "iPhone7", new BigDecimal(1000.0), Currency.getInstance("USD"), 10));
        assertNotNull(productDao.getProduct(id));
        productDao.delete(id);
    }

    @Test
    public void testDelete() {
        Long id = Product.generateId();
        productDao.save(new Product(id, "5", "iPhone7", new BigDecimal(1000.0), Currency.getInstance("USD"), 10));
        assertNotNull(productDao.getProduct(id));
        productDao.delete(id);
        assertNull(productDao.getProduct(id));
    }

    @Test
    public void testGetInstance() {
        assertNotNull(ArrayListProductDao.getInstance());
    }

    @Test
    public void testGetProduct() {
        assertNotNull(productDao.getProduct(productID));
    }

    @Test
    public void testFindProducts() {
        assertFalse(productDao.findProducts().isEmpty());
    }


}
