package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;
    @InjectMocks
    private ArrayListProductDao productDao = new ArrayListProductDao();

    @Before
    public void setup() {
        productDao.getProductList().addAll(Arrays.asList(product1, product2, product3));

        when(product1.getId()).thenReturn(1L);
        when(product1.getPrice()).thenReturn(new BigDecimal(100));
        when(product1.getStock()).thenReturn(10);

        when(product2.getId()).thenReturn(2L);
        when(product2.getPrice()).thenReturn(new BigDecimal(200));
        when(product2.getStock()).thenReturn(20);

        when(product3.getId()).thenReturn(3L);
        when(product4.getId()).thenReturn(4L);
    }

    @Test
    public void testFindProducts() {
        List<Product> findList = productDao.findProducts();
        assertTrue(findList.size() == 2
                && findList.containsAll(Arrays.asList(product1, product2)));
    }

    @Test
    public void testGetProduct() {
        assertEquals(productDao.getProduct(1L).getId(), product1.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNonExistentProduct() {
        productDao.getProduct(5L);
    }

    @Test
    public void testSaveProduct() {
        productDao.save(product4);
        assertTrue(productDao.getProductList().contains(product4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveExistentProduct() {
        productDao.save(product1);
    }

    @Test
    public void testDeleteProduct() {
        productDao.delete(3L);
        assertFalse(productDao.getProductList().contains(product3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNonExistentProduct() {
        productDao.delete(5L);
    }
}
