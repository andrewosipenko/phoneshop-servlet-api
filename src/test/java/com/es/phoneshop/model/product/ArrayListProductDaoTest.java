package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void testSuccessfulExecutionSaveAndFindProducts() {
        Product product = mock(Product.class);

        productDao.save(product);

        List<Product> expected = new ArrayList<>();
        expected.add(product);
        List<Product> actual = productDao.findProducts();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetProductNoResult() {
        Long realId = 1L;
        Long failId = 0L;
        Product product = mock(Product.class);

        when(product.getId()).thenReturn(realId);
        productDao.save(product);

        try {
            productDao.getProduct(failId);
            fail("Expected NoSuchElementException.");
        } catch (NoSuchElementException expected) {
            assertEquals(expected.getMessage(), failId.toString());
        }
    }

    @Test
    public void testSuccessfulExecutionGetProduct() {
        Long id = 1L;
        Product product = mock(Product.class);

        when(product.getId()).thenReturn(id);
        productDao.save(product);

        Product expected = product;
        Product actual = productDao.getProduct(id);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteProductNoResult() {
        Long realId = 1L;
        Long failId = 0L;
        Product product = mock(Product.class);

        when(product.getId()).thenReturn(realId);
        productDao.save(product);

        try {
            productDao.delete(failId);
            fail("Expected NoSuchElementException.");
        } catch (NoSuchElementException expected) {
            assertEquals(expected.getMessage(), failId.toString());
        }
    }

    @Test
    public void testSuccessfulExecutionDeleteProduct() {
        Long id = 1L;
        Product product = mock(Product.class);

        when(product.getId()).thenReturn(id);

        productDao.save(product);
        productDao.delete(id);

        assertTrue(productDao.findProducts().isEmpty());
    }
}
