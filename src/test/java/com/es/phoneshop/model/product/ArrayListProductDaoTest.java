package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {

    @Mock
    private Product productWithoutPrice;

    @Mock
    private Product productOutOfStock;

    @Mock
    private Product rightProduct;

    @Mock
    private Product productForAdding;

    @Mock
    private Product productForDelete;

    @Spy
    private List<Product> products = new ArrayList<>();

    @InjectMocks
    private ProductDao productDao = new ArrayListProductDao();

    @Before
    public void setup() {
        products.addAll(Arrays.asList(productWithoutPrice, productOutOfStock, rightProduct));

        when(productWithoutPrice.getId()).thenReturn(1L);
        when(productOutOfStock.getId()).thenReturn(2L);
        when(productOutOfStock.getPrice()).thenReturn(new BigDecimal(1000));
        when(rightProduct.getId()).thenReturn(3L);
        when(rightProduct.getPrice()).thenReturn(new BigDecimal(1000));
        when(rightProduct.getStock()).thenReturn(1000);
        when(productForDelete.getId()).thenReturn(4L);
    }

    @Test
    public void testGetProduct() {
        assertEquals(productWithoutPrice, productDao.getProduct(1L));
    }

    @Test
    public void testFindProducts() {
        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testSaveProduct() {
        productDao.save(productForAdding);
        assertEquals(4, products.size());

        products.remove(productForAdding);
    }

    @Test
    public void testDeleteProduct() {
        products.add(productForDelete);

        productDao.delete(4L);
        assertEquals(3, products.size());
    }
}
