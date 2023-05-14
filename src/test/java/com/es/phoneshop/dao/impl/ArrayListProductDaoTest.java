package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @BeforeEach
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testGetProductByIdNotNull() {
        Long id = 1L;

        assertNotNull(productDao.getProduct(id));
    }

    @Test
    public void testGetProductById() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        product.setId(1L);

        assertEquals(product.getCode(), productDao.getProduct(1L).getCode());
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsWithNotNullPriceAndMoreThanZeroStock() {
        int expectedValue = 0;

        List<Product> productList = productDao.findProducts();
        List<Product> checkedProductList = productList.stream().filter(product -> product.getPrice() == null && product.getStock() <= 0).collect(Collectors.toList());

        assertNotEquals(expectedValue, productList.size());
        assertEquals(expectedValue, checkedProductList.size());
    }

    @Test
    public void testSaveProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("iphone7", "Apple iPhone 7", new BigDecimal(1100), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        assertNotNull(productDao.getProduct(product.getId()));
    }

    @Test
    public void testUpdateProduct() {
        Currency usd = Currency.getInstance("USD");
        List<Product> products = productDao.findProducts();
        Product product = new Product(1L, "sgs", "Apple iPhone 7", new BigDecimal(1300), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        products.set(0, product);

        assertEquals(products.get(0), product);

        productDao.save(product);

        assertNotNull(productDao.getProduct(product.getId()));
    }

    @Test
    public void testDeleteProduct() {
        int initialSize = productDao.findProducts().size();

        productDao.delete(1L);

        assertEquals(initialSize - 1, productDao.findProducts().size());
    }

    @Test
    void testExceptionForEnableFoundProductById() {
        assertThrows(IllegalArgumentException.class, ()-> productDao.getProduct(null));
    }

    @Test
    void testExceptionForNotFoundProductById() {
        assertThrows(ProductNotFoundException.class, ()-> productDao.getProduct(50L));
    }

    @Test
    void testExceptionForEnableSaveProduct() {
        assertThrows(IllegalArgumentException.class, ()-> productDao.save(null));
    }
}
