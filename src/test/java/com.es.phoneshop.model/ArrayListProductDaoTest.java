package com.es.phoneshop.model;


import org.junit.Assert;
import org.junit.Before;
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


    @org.junit.Test
    public void save() {
        Long id = Product.generateId();
        assertNull(productDao.getProduct(id));
        productDao.save(new Product(id, "5", "iPhone7", new BigDecimal(1000.0), Currency.getInstance("USD"), 10));
        assertNotNull(productDao.getProduct(id));
        productDao.delete(id);
    }

    @org.junit.Test
    public void delete() {
        Long id = Product.generateId();
        productDao.save(new Product(id, "5", "iPhone7", new BigDecimal(1000.0), Currency.getInstance("USD"), 10));
        assertNotNull(productDao.getProduct(id));
        productDao.delete(id);
        assertNull(productDao.getProduct(id));
    }

    @org.junit.Test
    public void getInstance() {
        assertNotNull(ArrayListProductDao.getInstance());
    }

    @org.junit.Test
    public void getProduct() {
        assertNotNull(productDao.getProduct(productID));
    }

    @org.junit.Test
    public void FindProducts() {
        assertFalse(productDao.findProducts().isEmpty());
    }


}
