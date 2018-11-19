package com.es.phoneshop.model.product;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private final Long ID = 1L;
    private final String CODE = "sgs";
    private final String DESCRIPTION = "Samsung Galaxy S";
    private final BigDecimal PRICE = new BigDecimal(100);
    private final Currency CURRENCY = Currency.getInstance("USD");
    private final int STOCK = 100;
    private final String IMAGE_URL = "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg";

    private ProductDao productDao;
    private Product product;
    private List<Product> products = null;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        product = new Product(ID, CODE, DESCRIPTION, PRICE, CURRENCY, STOCK, IMAGE_URL);
        products = new ArrayList<>();
        products.add(product);
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductValidProduct(){
        assertNotNull(products);
        assertEquals(1, products.size());
        products.forEach(this::assertProduct);
    }

    public void assertProduct(Product product){
        assertNotNull(product);
        assertEquals(ID, product.getId());
        assertEquals(CODE, product.getCode());
        assertEquals(DESCRIPTION, product.getDescription());
        assertEquals(PRICE, product.getPrice());
        assertEquals(CURRENCY, product.getCurrency());
        assertEquals(STOCK, product.getStock());
        assertEquals(IMAGE_URL, product.getImageUrl());
    }

    @Test
    public void testFindProductWhenPriceIsNull(){
        product.setPrice(null);
        products.add(product);
        productDao.save(product);

        List<Product> list = productDao.findProducts();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testFindProductWithNotPositiveStock(){
        product.setStock(-2);
        products.add(product);
        productDao.save(product);

        List<Product> list = productDao.findProducts();
        assertTrue(list.isEmpty());
    }

}
