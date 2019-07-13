package com.es.phoneshop.model.product;


import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private static ProductDao productDao;

    @BeforeClass
    public static void setup() {
        productDao = new ArrayListProductDao();
        System.out.println(productDao.findProducts());
    }

    @Test
    public void testFindProductsResults() {
        assertTrue(!productDao.findProducts().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductsIllegalArgumentException(){
        productDao.getProduct(null);
    }

    @Test
    public void testGetProduct(){
        List<Product> products = productDao.findProducts();
        assertEquals(products.get(0).getId(),productDao.getProduct(products.get(0).getId()).getId());
    }

    @Test
    public void testSave(){
        Product product = new Product();
        product.setId(100L);
        productDao.save(product);
        assertEquals(product.getId(),productDao.getProduct(product.getId()).getId());
    }

    @Test
    public void testDelete(){
        List<Product> products = productDao.findProducts();
        int size = products.size();
        productDao.delete(products.get(0).getId());
        assertEquals(size-1,productDao.findProducts().size());
    }
}
