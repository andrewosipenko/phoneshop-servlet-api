package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ArrayListProductDaoTest
{
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
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsNonNullPrice() {
        int  size = productDao.findProducts().size();

        when(productMock.getPrice()).thenReturn(null);

        productDao.save(productMock);

        assertEquals(size, productDao.findProducts().size());
    }

    @Test
    public void testFindProductsNonNullStock() {
        int  size = productDao.findProducts().size();

        when(productMock.getStock()).thenReturn(0);

        productDao.save(productMock);

        assertEquals(size, productDao.findProducts().size());
    }

    @Test
    public void testSaveNewProductWithNullId() {
        productDao.save(product);

        assertTrue(product.getId() > 0);

        Optional<Product> result = productDao.getProduct(product.getId());

        assertTrue(result.isPresent());
        assertEquals("test-product", result.get().getCode());
    }

    // There is a product with the given id in DAO
    @Test
    public void testSaveNewProductWithExistingId() {
        product.setId(1L);
        productDao.save(product);

        Optional<Product> result = productDao.getProduct(product.getId());

        assertTrue(result.isPresent());
        assertEquals("test-product", result.get().getCode());
        assertEquals(1L, product.getId().longValue());
    }

    // The given id is greater than current (max) id in DAO
    @Test
    public void testSaveNewProductWithGreaterId() {
        productDao.save(product);
        long maxId = product.getId();
        long testId = maxId * 2;

        product.setId(testId);
        productDao.save(product);

        Optional<Product> result = productDao.getProduct(product.getId());

        assertTrue(result.isPresent());
        assertEquals("test-product", result.get().getCode());
        assertEquals(testId, product.getId().longValue());
    }

    // There is no product with the given id in DAO and the given id is lesser than current (max) id in DAO
    @Test
    public void testSaveNewProductWithLesserId() {
        product.setId(200L);
        productDao.save(product);

        long testId = 100;
        product.setId(testId);
        productDao.save(product);

        Optional<Product> result = productDao.getProduct(product.getId());

        assertTrue(result.isPresent());
        assertEquals("test-product", result.get().getCode());
        assertEquals(testId, product.getId().longValue());
    }

    @Test
    public void testDeleteProduct() {
        productDao.save(product);

        productDao.delete(product.getId());

        boolean result = productDao.getProduct(product.getId()).isPresent();
        assertFalse(result);
    }
}
