package com.es.phoneshop.domain.product.persistence;

import com.es.phoneshop.domain.product.model.Product;
import com.es.phoneshop.utils.LongIdGeneratorImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;


public class ArrayListProductDaoTest {

    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao(new LongIdGeneratorImpl());
    }

    @Test
    public void testGetAllAvailableProducts() {
        assertFalse(productDao.getAllAvailable().isEmpty());
    }

    @Test
    public void testGetProductById() {
        Long id = 1L;
        assertNotNull(productDao.getByIdOrNull(id));
    }

    @Test
    public void testGetProductByWrongIdNoResult() {
        Long id = 50L;
        assertNull(productDao.getByIdOrNull(id));
    }

    @Test
    public void testUpdateProduct() {
        Long id = 1L;
        Product toUpdate = new Product(id, "code", "description", new BigDecimal(100), Currency.getInstance("USD"), 10, "");
        productDao.update(toUpdate);
        Product updated = productDao.getByIdOrNull(id);
        assertEquals(toUpdate, updated);
    }

    @Test(expected = ProductPresistenceException.class)
    public void testUpdateProductWrongId() {
        Long id = 0L;
        Product toUpdate = new Product(id, "code", "description", new BigDecimal(100), Currency.getInstance("USD"), 10, "");
        productDao.update(toUpdate);
    }

    @Test
    public void testCreateProduct() {
        int initialSize = productDao.getAllAvailable().size();
        productDao.create(new Product(null, "code", "description", new BigDecimal(100), Currency.getInstance("USD"), 10, ""));
        int resultSize = productDao.getAllAvailable().size();
        assertEquals(initialSize + 1, resultSize);
    }


}