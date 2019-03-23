package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Test(expected = NullPointerException.class)
    public void testGetProductNullPointer() throws ProductNotFoundException {
        productDao.getProduct(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductIllegalArgument() throws ProductNotFoundException {
        productDao.getProduct(-5L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProduct() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        productDao.save(product);
        assertEquals(product.getId(), productDao.getProduct(product.getId()).getId());
    }


    @Test(expected = NullPointerException.class)
    public void testDeleteProductNullPointer() throws ProductNotFoundException {
        productDao.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteProductIllegalArgument() throws ProductNotFoundException {
        productDao.delete(-5L);
    }


    @Test(expected = NullPointerException.class)
    public void testSaveProductNullPointer() {
        productDao.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductIllegalArgument() {
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(1L); // setting up same id
        productDao.save(product1);
        productDao.save(product2);
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setStock(2);
        product.setPrice(new BigDecimal(1.0));
        productDao.save(product);
        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testFindProductWithCondition() {
        Product negativeStockProduct = new Product();
        negativeStockProduct.setStock(-5);
        negativeStockProduct.setId(1L);
        Product nullPriceProduct = new Product();
        nullPriceProduct.setPrice(null);
        nullPriceProduct.setId(2L);
        productDao.save(negativeStockProduct);
        productDao.save(nullPriceProduct);
        assertEquals(0, productDao.findProducts().size());
    }
}
