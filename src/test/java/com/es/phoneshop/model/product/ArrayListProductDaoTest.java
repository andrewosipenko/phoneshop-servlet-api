package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import java.math.*;
import java.util.*;


import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    
    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(!productDao.findProducts().isEmpty());
    }
    
    @Test
    public void testGetProduct(){
        assertTrue(productDao.getProduct(4L)!=null);
    }
    
    @Test
    public void testSaveProduct(){
        Product prod = new Product(14L, "nokia111", "Nokia 355", new BigDecimal(50), Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg" );
        productDao.save(prod);
        assertTrue(productDao.getProduct(prod.getId())!=null);
    }
    
    @Test
    public void testDeleteProduct(){
        productDao.delete(10L);
        assertTrue(productDao.getProd(10L)==false);
    }
}
