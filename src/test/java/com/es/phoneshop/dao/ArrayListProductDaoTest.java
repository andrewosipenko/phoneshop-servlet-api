package com.es.phoneshop.dao;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Product product;
    private Currency usd;

    @Mock
    private Product productMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        productDao = new ArrayListProductDao();
        product = new Product("test-product", "Samsung Galaxy S",
                new BigDecimal(100), usd, 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        usd = Currency.getInstance("USD");

        when(productMock.getId()).thenReturn(null);
        when(productMock.getPrice()).thenReturn(new BigDecimal(300));
        when(productMock.getStock()).thenReturn(10);
        when(productMock.getCode()).thenReturn("test-product-mock");
        when(productMock.getDescription()).thenReturn("The best phone ever");
    }

    @Test
    public void testSaveSampleProducts() {
        assertFalse(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductNullId() {
        productDao.getProduct(null);
    }

    @Test
    public void testGetExistingProduct() {
        Product result = productDao.getProduct(1L);
        assertEquals(1L, result.getId().longValue());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetNonExistingProduct() {
        productDao.getProduct(666L);
    }

    @Test
    public void testFindProductsNonNullPrice() {
        int size = productDao.findProducts(null, null, null).size();
        when(productMock.getPrice()).thenReturn(null);
        productDao.save(productMock);
        assertEquals(size, productDao.findProducts(null, null, null).size());
    }

    @Test
    public void testFindProductsNonNullStock() {
        int size = productDao.findProducts(null, null, null).size();
        when(productMock.getStock()).thenReturn(0);
        productDao.save(productMock);
        assertEquals(size, productDao.findProducts(null, null, null).size());
    }

    @Test
    public void testFindProductsByQuery() {
        String query = "best phone";
        productDao.save(productMock);

        assertEquals(productMock, productDao.findProducts(query, null, null).get(0));
    }

    @Test
    public void testFindProductsSortedByDescriptionAscOrder() {
        when(productMock.getDescription()).thenReturn("AAA");
        productDao.save(productMock);

        Product result = productDao.findProducts(null,
                SortField.description, SortOrder.asc).get(0);

        assertEquals(productMock, result);
    }

    @Test
    public void testFindProductsSortedByDescriptionDescOrder() {
        when(productMock.getDescription()).thenReturn("ZZZ");
        productDao.save(productMock);

        Product result = productDao.findProducts(null,
                SortField.description, SortOrder.desc).get(0);

        assertEquals(productMock, result);
    }

    @Test
    public void testFindProductsSortedByPriceAscOrder() {
        when(productMock.getPrice()).thenReturn(new BigDecimal(1));
        productDao.save(productMock);

        Product result = productDao.findProducts(null,
                SortField.price, SortOrder.asc).get(0);

        assertEquals(productMock, result);
    }

    @Test
    public void testFindProductsSortedByPriceDescOrder() {
        when(productMock.getPrice()).thenReturn(new BigDecimal(99999));
        productDao.save(productMock);

        Product result = productDao.findProducts(null,
                SortField.price, SortOrder.desc).get(0);

        assertEquals(productMock, result);
    }

    @Test
    public void testSaveNewProductWithNullId() {
        productDao.save(product);

        assertTrue(product.getId() > 0);

        Product result = productDao.getProduct(product.getId());

        assertEquals(product, result);
        assertEquals("test-product", result.getCode());
    }

    @Test
    public void testSaveNewProductWithExistingId() {
        when(productMock.getId()).thenReturn(1L);
        productDao.save(productMock);

        Product result = productDao.getProduct(productMock.getId());

        assertEquals(productMock, result);
        assertEquals("test-product-mock", result.getCode());
        assertEquals(1L, productMock.getId().longValue());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testSaveNewProductWithNonExistingId() {
        when(productMock.getId()).thenReturn(666L);
        productDao.save(productMock);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteProductWithExistingId() {
        productDao.delete(1L);
        productDao.getProduct(1L);
    }

    @Test
    public void testDeleteProductWithNonExistingId() {
        int size = productDao.findProducts(null, null, null).size();
        productDao.delete(123L);
        assertEquals(size, productDao.findProducts(null, null, null).size());
    }
}
