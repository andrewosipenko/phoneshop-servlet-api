package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private static final Long ID = 1L;
    private ProductDao productDao;
    private Product product;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        product = new Product();
        product.setId(ID);
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProduct() {
        product.setPrice(new BigDecimal(10));
        product.setStock(12);
        productDao.save(product);
        assertNotNull(productDao.findProducts());
    }

    @Test
    public void testFindProductAfterSavingWithStockLess0() {
        product.setPrice(new BigDecimal(1));
        product.setStock(0);
        productDao.save(product);
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProductById() {
        product.setPrice(new BigDecimal(1));
        product.setStock(1);
        productDao.save(product);
        assertEquals(ID, productDao.getProduct(ID).getId());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductByIdNoResult() {
        productDao.getProduct(ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductWithNullId(){
        product.setId(null);
        productDao.save(product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductWithTheSameId(){
        product.setId(ID);
        Product productWithTheSameId = new Product();
        productWithTheSameId.setId(ID);
        productDao.save(product);
        productDao.save(productWithTheSameId);
    }

    @Test
    public void testDelete() {
        productDao.save(product);
        productDao.delete(ID);
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindByDescription() {
        int stock = 1;
        BigDecimal price = new BigDecimal(1);
        Product product1 = new Product();
        product1.setDescription("Samsung Galaxy S");
        product1.setId(1L);
        product1.setPrice(price);
        product1.setStock(stock);
        Product product2 = new Product();
        product2.setDescription("Samsung Galaxy S III");
        product2.setId(2L);
        product2.setPrice(price);
        product2.setStock(stock);
        List<Product> controlProducts = new ArrayList<>();
        controlProducts.add(product1);
        controlProducts.add(product2);
        productDao.save(product1);
        productDao.save(product2);
        assertEquals(controlProducts, productDao.findProductsByDescription("Samsung"));
    }

    @Test
    public void testFindByDescriptionNoResult() {
        int stock = 1;
        BigDecimal price = new BigDecimal(1);
        Product product1 = new Product();
        product1.setDescription("Samsung Galaxy S");
        product1.setId(1L);
        product1.setPrice(price);
        product1.setStock(stock);
        productDao.save(product1);
        assertTrue(productDao.findProductsByDescription("Nokia").isEmpty());
    }
}
