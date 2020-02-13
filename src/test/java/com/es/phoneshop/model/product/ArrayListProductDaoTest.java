package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {

    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product productToSave;
    @Spy
    private ArrayList<Product> products;

    @InjectMocks
    private ArrayListProductDao productDao = new ArrayListProductDao();

    @Before
    public void setup() {
        products.addAll(Arrays.asList(product1, product2));
        when(product1.getId()).thenReturn(1L);
        when(product1.getPrice()).thenReturn(new BigDecimal(200));
        when(product1.getStock()).thenReturn(23);

        when(product2.getId()).thenReturn(2L);
        when(product2.getPrice()).thenReturn(new BigDecimal(300));
        when(product2.getStock()).thenReturn(42);
    }

    @Test
    public void testGetProduct() {
        assertEquals(product1.getId(), productDao.getProduct(1L).getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNullIdProduct() {
        productDao.getProduct(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetProductWithWrongId() {
        productDao.getProduct(3L);
    }

    @Test
    public void testFindProducts() {
        assertEquals(2, productDao.findProducts().size());
    }

    @Test
    public void testFindProductsNoResults() {
        products.clear();
        assertEquals(0, productDao.findProducts().size());
    }

    @Test
    public void testFindNullPriceProducts() {
        when(product1.getPrice()).thenReturn(null);
        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testFindZeroStockProducts() {
        when(product1.getStock()).thenReturn(0);
        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testSaveProduct() {
        when(productToSave.getId()).thenReturn(3L);
        productDao.save(productToSave);
        assertEquals(3, products.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveNullProduct() {
        productDao.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveNullIdProduct() {
        when(productToSave.getId()).thenReturn(null);
        productDao.save(productToSave);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveEqualIdProducts() {
        when(productToSave.getId()).thenReturn(2L);
        productDao.save(productToSave);
        assertEquals(2, products.size());
    }

    @Test
    public void testDeleteProduct() {
        productDao.delete(1L);
        assertFalse(products.contains(product1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNullIdProduct() {
        productDao.delete(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteProductWithWrongId() {
        productDao.delete(3L);
    }
}
