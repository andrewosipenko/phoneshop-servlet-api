package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();;

    @Mock
    private Product product;
    @Mock
    private Product product1;

    @Before
    public void setup() {
        productDao.getProductList().clear();

        productDao.save(product);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductBadID() {
        productDao.getProduct(-1L);
    }

    @Test
    public void testGetProduct() {
        assertEquals(product, productDao.getProduct(product.getId()));
    }

    @Test
    public void testFindProductsNoResults() {
       when(product.getStock()).thenReturn(1);

        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsWithoutStockOrPrice() {
        when(product.getId()).thenReturn(1L);
        when(product1.getId()).thenReturn(2L);
        int size = productDao.findProducts().size();

        productDao.save(product1);

        assertEquals(size, productDao.findProducts().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveBadProduct() {
        when(product.getId()).thenReturn(1L);

        productDao.save(product);
    }

    @Test
    public void testSave() {
        int oldSize = productDao.getProductList().size();
        when(product.getId()).thenReturn(1L);
        when(product1.getId()).thenReturn(2L);

        productDao.save(product1);

        assertEquals(productDao.getProductList().size(), oldSize + 1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNonexistentProduct() {
        productDao.delete(-1L);
    }

    @Test
    public void testDelete() {
        when(product.getId()).thenReturn(1L);
        int oldSize = productDao.getProductList().size();

        productDao.delete(product.getId());

        assertEquals(productDao.getProductList().size(), oldSize - 1);
    }
}