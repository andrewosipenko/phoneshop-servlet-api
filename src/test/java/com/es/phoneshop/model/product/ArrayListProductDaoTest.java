package com.es.phoneshop.model.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsNegativePrice() {
        ProductDao actualProductDao = new ArrayListProductDao();
        Product product = new Product();
        product.setPrice(new BigDecimal(-1));
        actualProductDao.save(product);
        Assert.assertEquals(productDao.findProducts().size(), productDao.findProducts().size());
    }

    @Test
    public void testFindProductZeroStock() {
        ProductDao actualProductDao = new ArrayListProductDao();
        Product product = new Product();
        product.setStock(0);
        actualProductDao.save(product);
        Assert.assertEquals(productDao.findProducts().size(), productDao.findProducts().size());
    }
}
