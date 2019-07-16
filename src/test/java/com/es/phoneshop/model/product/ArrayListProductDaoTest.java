package com.es.phoneshop.model.product;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ArrayListProductDaoTest {
    @Mock
    private Product product1;
    @Mock
    private Product product2;

    @InjectMocks
    private ArrayListProductDao productDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        product1.setId(1L);
        when(product1.getId()).thenReturn(1L);
        product2.setStock(1);
        product2.setPrice(new BigDecimal(100));
        when(product2.getId()).thenReturn(null);
        when(product2.getStock()).thenReturn(1);
        when(product2.getPrice()).thenReturn(new BigDecimal(100));
    }

    @Test
    public void testFindProductsResults() {
        assertTrue(!productDao.findProducts().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductsIllegalArgumentException() {
        productDao.getProduct(null);
    }

    @Test
    public void testGetProduct() {
        Long id = product1.getId();
        assertEquals(id, productDao.getProduct(id).getId());
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
        int size = products.size();
        productDao.delete(products.get(0).getId());
        assertEquals(size - 1, productDao.findProducts().size());
    }
}
