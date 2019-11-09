package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Mock
    private Product product;
    @Mock
    private Product product1;
    @Mock
    private Product product2;

    @Before
    public void setup() {
        productDao.getProductList().clear();

        when(product.getId()).thenReturn(0L);
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);

        productDao.save(product);
        productDao.save(product1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductBadID() {
        productDao.getProduct(-1L);
    }

    @Test
    public void testGetProduct() {
        assertEquals(product, productDao.getProduct(product.getId()));
    }

    @Test
    public void testFindProducts() {
        assertEquals(productDao.findProducts().size(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveBadProduct() {
        productDao.save(product);
    }

    @Test
    public void testSave() {
        int oldSize = productDao.getProductList().size();

        productDao.save(product2);

        assertEquals(productDao.getProductList().size(), oldSize + 1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNonexistentProduct() {
        productDao.delete(-1L);
    }

    @Test
    public void testDelete() {
        int oldSize = productDao.getProductList().size();

        productDao.delete(product.getId());

        assertEquals(productDao.getProductList().size(), oldSize - 1);
    }
}