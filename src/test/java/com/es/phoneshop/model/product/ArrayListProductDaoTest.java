package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProduct(){
        Product testProduct = new Product(35L);
        productDao.save(testProduct);
        Product prd = productDao.getProduct(35L);
        assertEquals(testProduct,prd);
    }

    @Test
    public void testGetProductWrong(){
        Product testProduct = new Product(10L);
        productDao.save(testProduct);
        Product wrongProduct = productDao.getProduct(15L);
        assertNotEquals(testProduct,wrongProduct);

    }

    @Test
    public void testSave(){
        Product product = new Product(56L);
        productDao.save(product);
        assertEquals(product,productDao.getProduct(product.getId()));
    }

    @Test
    public void testSaveDuplicate(){
        Product product1 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 =new Product(1L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productDao.save(product1);
        productDao.save(product2);
        List<Product> products = productDao.findProducts();
        assertEquals(products.indexOf(product1),products.lastIndexOf(product2));
    }

    @Test
    public void testDelete(){
        boolean before, after;
        productDao.save(new Product(10L));
        before = (productDao.getProduct(10L)!=null);
        productDao.delete(10L);
        after = productDao.getProduct(10L) == null;
        assertTrue(before && after);
    }
}
