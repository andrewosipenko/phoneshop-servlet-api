package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @InjectMocks
    private final ArrayListProductDao productDao = new ArrayListProductDao();

    private List<Product> testProducts;

    @Mock
    private Product product1;

    @Mock
    private Product product2;

    @Mock
    private Product product3;

    @Mock
    private Product productToSave;

    @Before
    public void setup() {
        testProducts = new ArrayList<>();
        when(product1.getId()).thenReturn(15L);
        when(product1.getPrice()).thenReturn(null);
        testProducts.add(product1);

        when(product2.getPrice()).thenReturn(new BigDecimal(130));
        when(product2.getStock()).thenReturn(30);
        testProducts.add(product2);

        testProducts.add(product3);

        when(productToSave.getId()).thenReturn(null);

        productDao.setProductList(testProducts);

    }

    @Test
    public void testGetProduct() throws ProductNotFoundException {
        Product actualProduct = productDao.getProduct(15L);
        assertEquals(product1, actualProduct);

    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductException() throws ProductNotFoundException {
        productDao.getProduct(14L);

    }

    @Test(expected = ProductNotFoundException.class)
    public void testIfProductsWithNullPriceOrZeroStockFound() throws ProductNotFoundException {
        productDao.findProducts().stream()
                .filter(product -> product.getPrice() == null || product.getStock() <= 0)
                .findAny()
                .orElseThrow(ProductNotFoundException::new);

    }

    @Test
    public void testSaveNewProduct() throws ProductNotFoundException {
        productDao.save(productToSave);
        verify(productToSave).setId(anyLong());
        assertTrue(testProducts.contains(productToSave));

    }

    @Test
    public void testSaveExistingProduct() throws ProductNotFoundException {
        productDao.save(product1);
        verify(product1, never()).setId(anyLong());
        assertTrue(testProducts.contains(product1));

    }

    @Test
    public void testDeleteProduct() throws ProductNotFoundException {
        long idToDelete = 15L;
        productDao.delete(idToDelete);
        assertTrue(!testProducts.contains(product1));

    }

}
