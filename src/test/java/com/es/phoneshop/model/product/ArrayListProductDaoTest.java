package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts("",null,null).isEmpty());
    }

    @Test
    public void testSaveNewProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);
        assertTrue(product.getId() > 0);
        assertTrue(productDao.getProduct(product.getId()).isPresent());
    }

    @Test
    public void testRewriteProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);

        Product replaceProduct = new Product(product.getId(), "replace", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(replaceProduct);

        Product result = productDao.getProduct(replaceProduct.getId()).get();
        assertEquals(result.getCode(), replaceProduct.getCode());
        assertTrue(productDao.getProduct(replaceProduct.getId()).isPresent());
        assertNotEquals(result.getCode(), product.getCode());
    }

    @Test
    public void testDeleteProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);
        productDao.delete(product.getId());
        assertFalse(productDao.getProduct(product.getId()).isPresent());
    }

    @Test
    public void testFindProducts(){
        assertTrue(productDao.findProducts("",null,null).stream()
                .noneMatch(product -> (product.getPrice() == null || product.getStock() <= 0)));

        assertEquals(productDao.findProducts("samsung",null,null).size(),
                productDao.findProducts("",null,null).stream()
                .filter(product -> product.getDescription().toLowerCase().contains("samsung")).count());
    }


}
