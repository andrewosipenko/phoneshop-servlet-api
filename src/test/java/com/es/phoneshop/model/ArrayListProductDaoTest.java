package com.es.phoneshop.model;

import com.es.phoneshop.web.ProductIDGenerator;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    ProductDao productDao = ArrayListProductDao.getInstance();

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
        product.setId((long)1);
        product.setCode(1 + "");
        product.setDescription("description" + 1);
        product.setPrice(new BigDecimal(1));
        product.setStock(1);
        expectedProducts[0] = product;
        assertArrayEquals(expectedProducts, products.toArray());
    }

    @Test
    public void getProduct() {
        Product product = new Product();
        product.setId((long)1);
        productDao.save(product);
        product = new Product();
        product.setId((long)2);
        product = new Product();
        product.setId((long)1);
        assertEquals(product, productDao.getProduct((long)1));
    }

    @Test
    public void save() {
        Product product = new Product();
        product.setId((long)1);
        product.setCode(1 + "");
        product.setDescription("description" + 1);
        product.setPrice(new BigDecimal(1));
        product.setStock(1);
        productDao.save(product);
        assertTrue(productDao.findProducts().contains(product));
    }

    @Test
    public void remove() {
        Product product = new Product();
        product.setId((long)1);
        product.setCode(1 + "");
        product.setDescription("description" + 1);
        product.setPrice(new BigDecimal(1));
        product.setStock(1);
        productDao.save(product);
        productDao.remove((long)1);
        assertFalse(productDao.findProducts().contains(product));
    }
}