package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testSaveNullProduct() {
        productDao.save(null);
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testProductSave() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getPrice()).thenReturn(BigDecimal.ONE);
        when(product.getStock()).thenReturn(1);
        productDao.save(product);
        assertEquals((long) productDao.findProducts().size(), 1L);
    }

    @Test
    public void testDelete() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getPrice()).thenReturn(BigDecimal.ONE);
        when(product.getStock()).thenReturn(1);
        productDao.save(product);
        productDao.delete(1L);
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testSaveSameId() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getPrice()).thenReturn(BigDecimal.ONE);
        when(product.getStock()).thenReturn(1);
        productDao.save(product);
        productDao.save(product);
        assertEquals((long) productDao.findProducts().size(), 1L);
    }

    @Test
    public void testNullPrice() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getStock()).thenReturn(1);
        productDao.save(product);
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testNotFoundId() {
        assertNull(productDao.getProduct(1L));
    }
}
