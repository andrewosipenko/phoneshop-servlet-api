package com.es.phoneshop.domain.product.persistence;

import com.es.phoneshop.domain.product.model.Product;
import com.es.phoneshop.domain.product.model.ProductRequest;
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
        assertFalse(productDao.getAll().isEmpty());
    }

    @Test
    public void testGetProductById() {
        Long id = 1L;
        assertTrue(productDao.getById(id).isPresent());
    }

    @Test
    public void testGetProductByWrongIdNoResult() {
        Long id = 50L;
        assertFalse(productDao.getById(id).isPresent());
    }

    @Test
    public void testUpdateProduct() {
        Long id = 1L;
        Product toUpdate = new Product(id, "code", "description", new BigDecimal(100), Currency.getInstance("USD"), 10, "");
        productDao.save(toUpdate);
        Product updated = productDao.getById(id).get();
        assertEquals(toUpdate, updated);
    }

    @Test
    public void testGetAllWithRequest() {
        int actualSize = productDao.getAll().size();
        int resultSize = productDao.getAll(new ProductRequest(null, null, null)).size();
        assertEquals(actualSize - 1, resultSize);
    }

    @Test
    public void testGetAllWithSearchQuery() {
        int availableSize = productDao.getAll(new ProductRequest("Samsung", null, null)).size();
        assertEquals(2, availableSize);
    }

    @Test
    public void testDeleteProduct() {
        Long id = 1L;
        productDao.delete(id);
        assertFalse(productDao.getById(id).isPresent());
    }

    @Test(expected = ProductPresistenceException.class)
    public void testDeleteProductWrongId() {
        Long id = 0L;
        productDao.delete(id);
    }

    @Test(expected = ProductPresistenceException.class)
    public void testUpdateProductWrongId() {
        Long id = 0L;
        Product toUpdate = new Product(id, "code", "description", new BigDecimal(100), Currency.getInstance("USD"), 10, "");
        productDao.save(toUpdate);
    }

    @Test
    public void testCreateProduct() {
        Long id = productDao.save(new Product(null, "code", "description", new BigDecimal(100), Currency.getInstance("USD"), 10, ""));
        assertTrue(productDao.getById(id).isPresent());
    }

    @Test
    public void testCreateProductMultiThread() throws InterruptedException {
        int initialSize = productDao.getAll().size();
        int threadsCount = 10;
        Thread[] threads = new Thread[threadsCount];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> productDao.save(new Product(null, "code", "description", new BigDecimal(100), Currency.getInstance("USD"), 10, null)));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int resultSize = productDao.getAll().size();
        assertEquals(initialSize + threadsCount, resultSize);
    }


    @Test
    public void testDeleteProductMultiThread() throws InterruptedException {
        int initialSize = productDao.getAll().size();
        int threadsCount = 10;
        Thread[] threads = new Thread[threadsCount];

        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> productDao.delete(finalI + 1L));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int resultSize = productDao.getAll().size();
        assertEquals(initialSize - threadsCount, resultSize);
    }


}