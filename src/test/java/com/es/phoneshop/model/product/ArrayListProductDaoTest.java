package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

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

        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteProduct() {

        productDao.delete(1L);
        productDao.getProduct(1L);
    }

    @Test
    public void testSaveNewProduct() {

        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-product", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productDao.save(product);
        Product result = productDao.getProduct(product.getId());
        assertEquals("test-product", result.getCode());
    }

    @Test
    public void testSaveExistingProduct() {

        Currency usd = Currency.getInstance("USD");
        Product product = new Product(1L,"test-product", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productDao.save(product);
        Product updatedProduct = new Product(1L,"test-product", "Samsung Galaxy S II", new BigDecimal(200), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productDao.save(updatedProduct);
        assertNotEquals(product.getStock(), updatedProduct.getStock());
    }

    @Test
    public void testFindProductWithZeroStock() {

        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productDao.save(product);
        List<Product> list = productDao.findProducts();
        assertFalse(list.contains(product));
    }

    @Test
    public void testFindProductWithNullPrice() {

        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs2", "Samsung Galaxy S II", null, usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productDao.save(product);
        List<Product> list = productDao.findProducts();
        assertFalse(list.contains(product));
    }
}
