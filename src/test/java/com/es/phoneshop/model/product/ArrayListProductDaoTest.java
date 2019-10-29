package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Mock
    private Product product, newProduct;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();

        when(product.getId()).thenReturn(1L);
        when(newProduct.getId()).thenReturn(14L);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetProductBadID() {
        productDao.getProduct(-1L);
    }

    @Test
    public void testGetProductNotNull() {
        assertNotNull(productDao.getProduct(product.getId()));
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsWithoutStockOrPrice() {
        assertTrue(productDao.findProducts().stream()
                .noneMatch(product -> product.getStock() <= 0 ||
                product.getPrice() == null));
    }

    @Test
    public void testFindProductsNotNull() {
        assertNotNull(productDao.findProducts());
    }

    @Test(expected = Exception.class)
    public void testSaveBadProduct() throws Exception {
        productDao.save(product);
    }

    @Test
    public void testSave() throws Exception {
        int oldSize = ((ArrayListProductDao)productDao).getProductList().size();

        productDao.save(newProduct);

        assertEquals(((ArrayListProductDao)productDao).getProductList().size(), oldSize + 1);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteNonexistentProduct() {
        productDao.delete(-1L);
    }

    @Test
    public void testDelete() {
        int oldSize = ((ArrayListProductDao)productDao).getProductList().size();

        productDao.delete(product.getId());

        assertEquals(((ArrayListProductDao)productDao).getProductList().size(), oldSize - 1);
    }
}
