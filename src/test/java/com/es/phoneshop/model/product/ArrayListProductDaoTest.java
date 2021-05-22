package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindExistingProductById() {
        Long id = 0L;
        Optional<Product> product = productDao.getProduct(id);
        assertTrue(product.isPresent());
        assertTrue(product.get().getPrice() != null);
        assertTrue(product.get().getStock() > 0);
    }

    @Test
    public void testFindNonexistentProductById() {
        Long id = -1L;
        assertFalse(productDao.getProduct(id).isPresent());
    }

    @Test
    public void testFindProducts() {
        List<Product> products = productDao.findProducts();
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(product -> product.getPrice() != null));
        assertTrue(products.stream().allMatch(product -> product.getStock() > 0));
    }

    @Test
    public void testSaveProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("product", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertTrue(product.getId() > 0);
        Product result = productDao.getProduct(product.getId()).get();
        assertNotNull(result);
        assertEquals(result.getCode(), "product");
        assertEquals(result.getDescription(), "Samsung Galaxy S");
        assertEquals(result.getPrice(), new BigDecimal(100));
        assertEquals(result.getCurrency(), usd);
        assertEquals(result.getStock(), 100);
        assertEquals(result.getImageUrl(), "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
    }

    @Test
    public void testFindProductWithZeroStock() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("product", "Samsung Galaxy S", new BigDecimal(100), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        Optional<Product> result = productDao.getProduct(product.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindProductWithNullPrice() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("product", "Samsung Galaxy S", null, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        Optional<Product> result = productDao.getProduct(product.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testDeleteExistingProduct() {
        Long id = 0L;
        productDao.delete(id);
        assertFalse(productDao.getProduct(id).isPresent());
    }
}
