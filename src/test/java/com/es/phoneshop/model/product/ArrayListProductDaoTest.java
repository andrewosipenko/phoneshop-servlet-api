package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testSaveNewProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        assertTrue(product.getId() > 0);
        Product result = productDao.getProduct(product.getId());
        assertNotNull(result);
        assertEquals("test-product", result.getCode());
    }

    @Test
    public void testProductWithZeroStock() {

        List<Product> products = productDao.findProducts();
        long count = products.stream()
                .filter(product -> product.getStock() == 0)
                .count();
        assertFalse(count > 0);

    }

    @Test
    public void testDeleteProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        assertTrue(product.getId() > 0);
        assertNotNull(productDao.getProduct(product.getId()));
        productDao.delete(product.getId());
        Product result = productDao.getProduct(product.getId());
        assertNull(result);

    }
}
