package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();;

    @Mock
    private Product product;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;

    @Before
    public void setup() {
        productDao.getProductList().clear();

        when(product.getId()).thenReturn(0L);
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);

        productDao.save(product);
        productDao.save(product1);
        productDao.save(product2);
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
        BigDecimal price = new BigDecimal(1);
        when(product.getPrice()).thenReturn(price);
        when(product1.getPrice()).thenReturn(price);
        when(product2.getPrice()).thenReturn(null);

       when(product.getStock()).thenReturn(1);
       when(product1.getStock()).thenReturn(0);
       when(product2.getStock()).thenReturn(1);

        assertEquals(productDao.findProducts().size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveBadProduct() {
        productDao.save(product);
    }

    @Test
    public void testSave() {
        int oldSize = productDao.getProductList().size();

        productDao.save(product3);

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