package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test(expected = NullPointerException.class)
    public void testGetProductNullPointer() {
        productDao.getProduct(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductIllegalArgument() {
        productDao.getProduct(-5L);
    }

    @Test
    public void testGetProduct() {
        Product product = new Product();
        product.setId(1L);
        productDao.save(product);
        assertEquals(product.getId(), productDao.getProduct(product.getId()).getId());
    }


    @Test(expected = NullPointerException.class)
    public void testDeleteProductNullPointer() {
        productDao.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteProductIllegalArgument() {
        productDao.delete(-5L);
    }


    @Test(expected = NullPointerException.class)
    public void testSaveProductNullPointer() {
        productDao.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductIllegalArgument() {
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(1L); // setting up same id
        productDao.save(product1);
        productDao.save(product2);
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        productDao.save(product);
        assertEquals(1, productDao.findProducts().size());
    }


    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }
}
