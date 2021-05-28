package com.es.phoneshop.domain.product.persistence;

import com.es.phoneshop.domain.product.model.Product;
import com.es.phoneshop.domain.product.model.ProductRequest;
import com.es.phoneshop.utils.LongIdGeneratorImpl;
import com.es.phoneshop.web.contextListeners.SampleDataServletContextListener;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;


public class ArrayListProductDaoTest {

    private static ProductDao productDao;
    private static List<Product> sampleProducts;

    @BeforeClass
    public static void setupAll() {
        sampleProducts = SampleDataServletContextListener.getSampleProducts();
    }

    @Before
    public void setup() {
        productDao = new ArrayListProductDao(
                new LongIdGeneratorImpl(0L),
                Clock.fixed(
                        Instant.parse("2021-01-01T00:00:00.00Z"),
                        ZoneId.of("Europe/London")
                )
        );
        sampleProducts.forEach(it -> productDao.save(
                new Product(
                        it.getId(),
                        it.getCode(),
                        it.getDescription(),
                        it.getPrice(),
                        it.getCurrency(),
                        it.getStock(),
                        it.getImageUrl())
                )
        );
    }

    @Test
    public void testGetAllByRequest() {
        assertFalse(productDao.getAllByRequest(new ProductRequest(null, null, null, 1)).isEmpty());
    }

    @Test
    public void testGetProductById() {
        Long id = 1L;
        assertTrue(productDao.getById(id).isPresent());
    }

    @Test
    public void testGetProductByWrongIdNoResult() {
        Long id = 500L;
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
        int resultSize = productDao.getAllByRequest(new ProductRequest(null, null, null, 1)).size();
        assertEquals(actualSize - 1, resultSize);
    }

    @Test
    public void testGetAllWithSearchQuery() {
        int productsSize = productDao.getAllByRequest(new ProductRequest("Samsung", null, null, 1)).size();
        assertEquals(2, productsSize);
    }

    @Test
    public void testDeleteProduct() {
        Long id = 1L;
        productDao.delete(id);
        assertFalse(productDao.getById(id).isPresent());
    }

    @Test(expected = ProductPresistenceException.class)
    public void testDeleteProductWrongId() {
        Long id = 500L;
        productDao.delete(id);
    }

    @Test(expected = ProductPresistenceException.class)
    public void testUpdateProductWrongId() {
        Long id = 500L;
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
            threads[i] = new Thread(() -> productDao.delete((long) finalI));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int resultSize = productDao.getAll().size();
        assertEquals(initialSize - threadsCount, resultSize);
    }

    @Test
    public void testGetPricesHistoryByProductId(){
        Long productId = 0L;
        assertEquals(1, productDao.getPricesHistoryByProductId(productId).size());

        productDao.save(new Product(productId, "code", "description", new BigDecimal(1000), Currency.getInstance("USD"), 12, null));
        assertEquals(2, productDao.getPricesHistoryByProductId(productId).size());

        productDao.save(new Product(productId, "code", "description", new BigDecimal(1000), Currency.getInstance("INR"), 12, null));
        assertEquals(3, productDao.getPricesHistoryByProductId(productId).size());

        productDao.save(new Product(productId, "code", "description", new BigDecimal(100), Currency.getInstance("INR"), 12, null));
        assertEquals(4, productDao.getPricesHistoryByProductId(productId).size());

        productDao.save(new Product(productId, "code", "description", new BigDecimal(1000), Currency.getInstance("USD"), 12, null));
        assertEquals(5, productDao.getPricesHistoryByProductId(productId).size());
    }


}