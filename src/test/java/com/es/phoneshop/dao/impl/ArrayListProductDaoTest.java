package com.es.phoneshop.dao.impl;

import com.es.phoneshop.model.enums.SortField;
import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exception.ProductNotFoundException;
import org.junit.After;
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
    @Mock
    private Product product5;
    @InjectMocks
    private ArrayListProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        productDao.getProductList().addAll(Arrays.asList(product1, product2, product3, product5));

        when(product1.getId()).thenReturn(1L);
        when(product1.getPrice()).thenReturn(new BigDecimal(100));
        when(product1.getStock()).thenReturn(10);
        when(product1.getDescription()).thenReturn("Apple iPhone 6");

        when(product2.getId()).thenReturn(2L);
        when(product2.getPrice()).thenReturn(new BigDecimal(200));
        when(product2.getStock()).thenReturn(20);
        when(product2.getDescription()).thenReturn("Samsung Galaxy S");

        when(product3.getId()).thenReturn(3L);
        when(product4.getId()).thenReturn(4L);

        when(product5.getId()).thenReturn(5L);
        when(product5.getPrice()).thenReturn(new BigDecimal(50));
        when(product5.getStock()).thenReturn(50);
        when(product5.getDescription()).thenReturn("Apple iPhone");

    }

    @After
    public void clearProductList() {
        productDao.getProductList().clear();
    }

    @Test
    public void testFindProducts() {
        List<Product> findList = productDao.findProducts(null, null, null);
        assertTrue(findList.size() == 3
                && findList.containsAll(Arrays.asList(product1, product2, product5)));
    }

    @Test
    public void testFindProductsWithQuery() {
        List<Product> findList = productDao.findProducts("Apple 6", null, null);
        assertEquals(findList, Arrays.asList(product1, product5));
    }

    @Test
    public void testFindProductsWithSort() {
        List<Product> findList = productDao.findProducts(null, SortField.PRICE, SortOrder.ASC);
        assertEquals(findList, Arrays.asList(product5, product1, product2));
    }

    @Test
    public void testFindProductsWithQueryAndSort() {
        List<Product> findList = productDao.findProducts("Apple 6", SortField.DESCRIPTION, SortOrder.DESC);
        assertEquals(findList, Arrays.asList(product1, product5));
    }

    @Test
    public void testGetProduct() {
        assertEquals(productDao.getProduct(1L).getId(), product1.getId());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetNonExistentProduct() {
        productDao.getProduct(10L);
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

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNonExistentProduct() {
        productDao.delete(10L);
    }
}
