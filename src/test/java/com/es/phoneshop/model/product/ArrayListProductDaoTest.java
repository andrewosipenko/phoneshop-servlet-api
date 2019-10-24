package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao = new ArrayListProductDao();

    @Mock
    private Product product;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;

    @Before
    public void setUp() {
        productDao.setProducts(new ArrayList<>(Arrays.asList(product1, product2, product3)));

        when(product1.getId()).thenReturn(0L);
        when(product2.getId()).thenReturn(1L);
        when(product3.getId()).thenReturn(2L);
    }

    @Test
    public void testGetProduct() {
        Product foundProduct = productDao.getProduct(0L);

        assertSame(product1, foundProduct);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNonExistingProduct() {
        productDao.getProduct(123L);
    }

    @Test
    public void testFindProducts() {
        BigDecimal notNullPrice = new BigDecimal(5);
        when(product1.getPrice()).thenReturn(notNullPrice);
        when(product2.getPrice()).thenReturn(null);
        when(product3.getPrice()).thenReturn(notNullPrice);

        when(product1.getStock()).thenReturn(0);
        when(product2.getStock()).thenReturn(24);
        when(product3.getStock()).thenReturn(24);


        List<Product> products = productDao.findProducts();
        assertTrue(products.size() == 1);
        assertSame(products.get(0), product3);
    }

    @Test
    public void testSaveProduct() {
        when(product.getId()).thenReturn(222L);
        productDao.save(product);
        assertSame(productDao.getProduct(product.getId()), product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveExistingProduct() {
        productDao.save(product1);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteProduct() {
        try {
            productDao.delete(product1.getId());
        } catch (NoSuchElementException e) {
            fail();
        }

        productDao.getProduct(product1.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteNonExistingProduct() {
        productDao.delete(213245L);
    }
}
