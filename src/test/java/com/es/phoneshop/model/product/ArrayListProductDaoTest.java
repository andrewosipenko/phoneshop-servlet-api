package com.es.phoneshop.model.product;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @Mock
    private Product product1;
    @Mock
    private Product product2;

    private ArrayListProductDao productDao = new ArrayListProductDao();

    @Before
    public void setup() {
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(null);
        when(product2.getStock()).thenReturn(1);
        when(product2.getPrice()).thenReturn(new BigDecimal(100));
    }

    @Test
    public void testFindProductsResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductsIllegalArgumentException() {
        productDao.getProduct(null);
    }

    @Test
    public void testGetProduct() {
        assertEquals((Long) 1L, productDao.getProduct(1L).getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveIllegalArgumentException() {
        productDao.save(product1);
    }

    @Test
    public void testSave() {
        int sizeBefore = productDao.findProducts().size();
        productDao.save(product2);
        assertEquals(sizeBefore + 1, productDao.findProducts().size());
    }

    @Test
    public void testDelete() {
        List<Product> products = productDao.findProducts();
        int sizeBefore = products.size();
        productDao.delete(products.get(0).getId());
        assertEquals(sizeBefore - 1, productDao.findProducts().size());
    }
}
