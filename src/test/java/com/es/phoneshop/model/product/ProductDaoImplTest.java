package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductDaoImplTest {
    private ProductDaoImpl productDao;
    private List<Product> testProducts;


    @Before
    public void setup() {
        productDao = ProductDaoImpl.getInstance();
        testProducts = new ArrayList<>();
    }

    @Test
    public void givenProductsArray_whenFindProducts_thenReturnNotEmptyArray() {
        testProducts.add(new Product("test", "Test product", new BigDecimal(100), Currency.getInstance("USD"), 10, ""));
        productDao.setProducts(testProducts);

        List<Product> result = productDao.findProducts();

        assertFalse(result.isEmpty());
    }

    @Test
    public void givenValidProduct_whenGetProduct_thenGetProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Test product", new BigDecimal(100), usd, 10, "");
        testProducts.add(product);
        productDao.setProducts(testProducts);

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
    public void givenValidProductArray_whenFindProduct_thenGetProductArray() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Test product", new BigDecimal(100), usd, 10, "");
        testProducts.add(product);
        productDao.setProducts(testProducts);

        List<Product> result = productDao.findProducts();

        assertNotNull(result);
    }

    @Test
    public void givenValidProduct_whenSaveProduct_thenGetSavedProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Test product", new BigDecimal(100), usd, 10, "");

        productDao.save(product);

        assertTrue(productDao.getProducts().contains(product));
    }

    @Test
    public void givenValidUpdatedProduct_whenUpdateProduct_thenGetUpdatedProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Test product", new BigDecimal(100), usd, 10, "");
        testProducts.add(product);
        productDao.setProducts(testProducts);
        Product updatedProduct = product;
        updatedProduct.setDescription("New description");
        updatedProduct.setStock(20);

        productDao.save(updatedProduct);
        Optional<Product> optionalProduct = productDao.getProduct(product.getId());

        assertTrue(optionalProduct.isPresent());
        updatedProduct = optionalProduct.get();
        assertEquals("New description", updatedProduct.getDescription());
        assertEquals(20, updatedProduct.getStock());
    }

    @Test
    public void givenValidProduct_whenDelete_thenDeleteProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, 1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testProducts.add(product);
        productDao.setProducts(testProducts);

        productDao.delete(product.getId());

        assertFalse(productDao.getProducts().contains(product));
    }

    @Test
    public void givenZeroStockProduct_whenFindProducts_thenNotFindZeroStockProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, -1, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testProducts.add(product);
        productDao.setProducts(testProducts);

        List<Product> products = productDao.findProducts();

        assertFalse(products.contains(product));
    }

    @Test(expected = ProductNotFoundException.class)
    public void givenNotValidId_whenDeleteProduct_thenThrowProductNotFoundException() throws ProductNotFoundException {
        testProducts = new ArrayList<>();
        long productId = 123L;
        productDao.setProducts(testProducts);

        productDao.delete(productId);
    }
}
