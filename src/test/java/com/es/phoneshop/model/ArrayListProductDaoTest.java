package com.es.phoneshop.model;

import com.es.phoneshop.model.product.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import java.util.*;


import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    Product product;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        Currency currency = Currency.getInstance("USD");
        product = new Product("test", "HTC EVO Shift 4G", new BigDecimal(320), currency, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
    }

    @Test

    public void testFindProductsWithSortFieldAndSortOrder() {
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("sgs", "A", new BigDecimal(100), usd, 130, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs3", "C", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product("sgs4", "B", new BigDecimal(400), usd, 78, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        List<Product> products = productDao.findProducts("", SortField.price, SortOrder.asc);
        assertTrue(products.get(0).getPrice().compareTo(products.get(1).getPrice()) < 0 || Objects.equals(products.get(0).getPrice(), products.get(1).getPrice()));
    }

    @Test
    public void testFindProductsWithSearchQuery() {
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("sgs", "A 2", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs3", "C 2", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product("sgs4", "A", new BigDecimal(400), usd, 78, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        List<Product> products = productDao.findProducts("A 2", null, null);

        assertEquals("A 2", products.get(0).getDescription());
        assertEquals("A", products.get(1).getDescription());
        assertEquals("C 2", products.get(2).getDescription());
    }

    @Test
    public void testFindProductsHasResults() {
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(120), usd, 110, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        assertFalse(productDao.findProducts("", null, null).isEmpty());
    }

    @Test
    public void testFindProductsReturnsNotNullPrice() {
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(110), usd, 300, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        List<Product> products = productDao.findProducts("", null, null);
        assertTrue(products.stream().allMatch(product -> product.getPrice() != null && product.getStock() > 0));
    }

    @Test

    public void testGetExistingProducts() {
        productDao.save(product);
        assertEquals("test", productDao.getProduct(product.getId()).getCode());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNonExistingProducts() {
        productDao.save(product);
        productDao.getProduct(product.getId() + 1);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetProductNullId() {
        productDao.getProduct(null);
    }

    @Test

    public void testSaveExistingProduct() {
        productDao.save(product);
        product.setCode("test-product");
        productDao.save(product);
        assertEquals("test-product", productDao.getProduct(product.getId()).getCode());
    }

    @Test
    public void testSaveNewProduct() {
        productDao.save(product);
        assertEquals(product.getCode(), productDao.getProduct(product.getId()).getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveNullProduct() {
        productDao.save(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteExistingProduct() {
        productDao.save(product);
        productDao.delete(product.getId());
        productDao.getProduct(product.getId());
    }

    @Test
    public void testDeleteNonExistingProduct() {
        List<Product> products = productDao.findProducts("", null, null);
        productDao.delete(Long.MAX_VALUE);
        productDao.delete(Long.MIN_VALUE);
        assertEquals(products, productDao.findProducts("", null, null));        
    }

    @Test
    public void testDeleteProductNullId() {
        List<Product> products = productDao.findProducts("", null, null);
        productDao.delete(null);
        assertEquals(products, productDao.findProducts("", null, null));
    }
}
