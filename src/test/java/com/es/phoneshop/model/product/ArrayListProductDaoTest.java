package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao;

    @Mock
    private Product product;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetProductBadID() {
        productDao.getProduct(-1L);
    }

    @Test
    public void testGetProductNotNull() {
        when(product.getId()).thenReturn(1L);

        assertNotNull(productDao.getProduct(product.getId()));
    }

    @Test
    public void testGetProduct() {
        productDao.save(product);

        assertEquals(product, productDao.getProduct(product.getId()));
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsWithoutStockOrPrice() {
        ProductDao testProductDao = new ArrayListProductDao();

        testProductDao.save(product);

        assertEquals(testProductDao.findProducts(), productDao.findProducts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveBadProduct() {
        when(product.getId()).thenReturn(1L);

        productDao.save(product);
    }

    @Test
    public void testSave() {
        int oldSize = (productDao).getProductList().size();
        when(product.getId()).thenReturn(14L);

        productDao.save(product);

        assertEquals(productDao.getProductList().size(), oldSize + 1);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteNonexistentProduct() {
        productDao.delete(-1L);
    }

    @Test
    public void testDelete() {
        int oldSize = (productDao).getProductList().size();
        when(product.getId()).thenReturn(1L);

        productDao.delete(product.getId());

        assertEquals((productDao).getProductList().size(), oldSize - 1);
    }
}
