package com.es.phoneshop.model;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Before
    public void clear() {
        setInternalState(productDao, "productList", new ArrayList<Product>());
    }

    @Test
    public void findProducts() {
        Product product = null;
        for (int i = 0; i < 3; i++) {
            product = new Product();
            productDao.generateID(product);
            product.setCode(i + "");
            product.setDescription("description" + i);
            product.setPrice(new BigDecimal(i));
            product.setStock(i);
            productDao.save(product);
        }
        Product[] expectedProducts = new Product[2];
        expectedProducts[1] = product;
        product = new Product();
        product.setId(1L);
        product.setCode(1 + "");
        product.setDescription("description" + 1);
        product.setPrice(new BigDecimal(1));
        product.setStock(1);
        expectedProducts[0] = product;
        List<Product> products = productDao.findProducts();
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
        when(product.getPrice()).thenReturn(BigDecimal.ONE);
        when(product.getStock()).thenReturn(1);
        productDao.save(product);
        assertTrue(productDao.findProducts().contains((Product) product));
    }

    @Test
    public void remove() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(3L);
        when(product.getPrice()).thenReturn(BigDecimal.ONE);
        when(product.getStock()).thenReturn(1);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        setInternalState(productDao, "productList", productList);
        productDao.remove(3L);
        assertFalse(productDao.findProducts().contains(product));
    }

    public void setInternalState(Object c, String field, Object value) {
        try {
            Field f = c.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(c, value);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException er) {
            er.printStackTrace();
        }
    }
}