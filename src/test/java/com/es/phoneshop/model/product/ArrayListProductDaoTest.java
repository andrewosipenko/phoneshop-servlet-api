package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private Product product1;
    private Product product2;



    @Before
    public void setup() {
        productDao = new ArrayListProductDao();


        product1 = Mockito.mock(Product.class);
        product2 = Mockito.mock(Product.class);

        when(product1.getId()).thenReturn(1L);
        when(product1.getPrice()).thenReturn(new BigDecimal(10));
        when(product1.getStock()).thenReturn(1);
        when(product2.getId()).thenReturn(2L);
        when(product2.getPrice()).thenReturn(new BigDecimal(20));
        when(product2.getStock()).thenReturn(2);
    }

    @Test
    public void deleteTest() {
        productDao.save(product1);
        try{
            productDao.delete(1L);
        }
        catch(NoSuchProductWithCurrentIdException e){
            e.printStackTrace();
        }
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void saveTest() {
        productDao.save(product1);
        assertEquals(Arrays.asList(product1), productDao.findProducts());
    }

    @Test
    public void getFromIdTest() {
        productDao.save(product1);
        try{
            assertEquals(product1, productDao.getProduct(1L));
        }
        catch(NoSuchProductWithCurrentIdException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProducts() {
        productDao.save(product1);
        productDao.save(product2);
        assertEquals(Arrays.asList(product1, product2), productDao.findProducts());
    }

}