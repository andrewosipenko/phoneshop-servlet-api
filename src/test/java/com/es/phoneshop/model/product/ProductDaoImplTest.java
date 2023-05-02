package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ProductDaoImplTest {
    private ProductDaoImpl productDao;

    @Before
    public void setup() {
        productDao = ProductDaoImpl.getInstance();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void givenValidProduct_whenSave_thenGetProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Test product", new BigDecimal(100), usd, 10, "");
        productDao.save(product);
        Optional<Product> optionalProduct = productDao.getProduct(product.getId());
        assertTrue(optionalProduct.isPresent());
        Product savedProduct = optionalProduct.get();
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getCode(), savedProduct.getCode());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getCurrency(), savedProduct.getCurrency());
        assertEquals(product.getStock(), savedProduct.getStock());
        assertEquals(product.getImageUrl(), savedProduct.getImageUrl());
    }

    @Test
    public void findProducts() {
        List<Product> products = productDao.findProducts();
        assertNotNull(products);
    }

    @Test
    public void givenValidProduct_whenUpdate_thenUpdateProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Test product", new BigDecimal(100), usd, 10, "");
        productDao.save(product);
        Optional<Product> optionalProduct = productDao.getProduct(product.getId());
        assertTrue(optionalProduct.isPresent());
        Product savedProduct = optionalProduct.get();
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getCode(), savedProduct.getCode());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getCurrency(), savedProduct.getCurrency());
        assertEquals(product.getStock(), savedProduct.getStock());
        assertEquals(product.getImageUrl(), savedProduct.getImageUrl());
        savedProduct.setDescription("New description");
        savedProduct.setStock(20);
        productDao.save(savedProduct);
        optionalProduct = productDao.getProduct(product.getId());
        assertTrue(optionalProduct.isPresent());
        savedProduct = optionalProduct.get();
        assertEquals("New description", savedProduct.getDescription());
        assertEquals(20, savedProduct.getStock());
    }

    @Test
    public void givenValidProduct_whenDelete_thenDeleteProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        List<Product> products = new ArrayList<>();
        Product product = new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, 1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        products.add(product);
        productDao.setProducts(products);
        assertTrue(productDao.getProducts().contains(product));
        productDao.delete(product.getId());
        assertFalse(productDao.getProducts().contains(product));
    }

    @Test
    public void givenZeroStockProduct_whenFindProducts_thenFindProducts() {
        Currency usd = Currency.getInstance("USD");
        List<Product> products = new ArrayList<>();
        Product product = new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, -1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        products.add(product);
        productDao.setProducts(products);
        List<Product> products1 = productDao.findProducts();
        assertFalse(products1.contains(product));
    }

    @Test(expected = ProductNotFoundException.class)
    public void givenNotValidId_whenDeleteProduct_thenDeleteProduct() throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        String productId = "123";
        productDao.setProducts(products);
        productDao.delete(productId);
    }
}
