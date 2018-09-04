package com.es.phoneshop.model;

import com.es.phoneshop.web.ProductIDGenerator;

import org.junit.Test;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Test
    public void findProducts() {
        Product product = null;
        for (int i = 0; i < 3; i++) {
            product = new Product();
            ProductIDGenerator.generateID(product);
            product.setCode(i + "");
            product.setDescription("description" + i);
            product.setPrice(new BigDecimal(i));
            product.setStock(i);
            productDao.save(product);
        }
        List<Product> products = productDao.findProducts();
        Product[] expectedProducts = new Product[2];
        expectedProducts[1] = product;
        product = new Product();
        product.setId(1L);
        product.setCode(1 + "");
        product.setDescription("description" + 1);
        product.setPrice(new BigDecimal(1));
        product.setStock(1);
        expectedProducts[0] = product;
        assertArrayEquals(expectedProducts, products.toArray());
    }

    @Test
    public void getProduct() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        productDao.save(product);
        assertEquals(product, productDao.getProduct(1L));
    }

    @Test
    public void save() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(3L);
        productDao.save(product);
        assertTrue(productDao.findProducts().contains(product));
    }

    @Test
    public void remove() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        productDao.save(product);
        productDao.remove(1L);
        assertFalse(productDao.findProducts().contains(product));
    }
}