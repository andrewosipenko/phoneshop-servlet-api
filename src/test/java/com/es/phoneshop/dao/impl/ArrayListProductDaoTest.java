package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.sorting.SortField;
import com.es.phoneshop.dao.sorting.SortOrder;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.web.listener.DemoDataServletContextListener;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ArrayListProductDaoTest {
    private static int numberOfSampleProducts; // id of the last product in dao

    private static final ProductDao productDao = ArrayListProductDao.getInstance();

    // Mocks
    private static final ServletContextEvent servletContextEvent = Mockito.mock(ServletContextEvent.class);
    private static final ServletContext servletContext = Mockito.mock(ServletContext.class);
    private final Product product = Mockito.mock(Product.class);

    @BeforeClass
    public static void setup() {
        DemoDataServletContextListener servletContextListener = new DemoDataServletContextListener();

        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(eq("insertDemoData"))).thenReturn("true");

        servletContextListener.contextInitialized(servletContextEvent);

        numberOfSampleProducts = productDao.findProducts(null, null, null).size();
    }


    // getProduct method tests

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductNullId() {
        productDao.getProduct(null);
    }

    @Test
    public void testGetExistingProduct() {
        Long testId = 1L;
        Product receivedProduct = productDao.getProduct(testId);
        assertEquals(testId, receivedProduct.getId());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetNonExistingProduct() {
        productDao.getProduct(666L);
    }

    // saveProduct method tests

    @Test
    public void testSaveNewProductWithNullId() {
        when(product.getId()).thenReturn(null);
        when(product.getPrice()).thenReturn(new BigDecimal(300));
        when(product.getStock()).thenReturn(10);
        productDao.save(product);

        Long productTestId = (long) (numberOfSampleProducts + 1);

        verify(product).setId(productTestId);

        int size = productDao.findProducts(null, null, null).size();

        assertEquals(numberOfSampleProducts + 1, size);
    }

    @Test
    public void testSaveNewProductWithExistingId() {
        Long testId = (long) numberOfSampleProducts;

        when(product.getId()).thenReturn(testId);

        productDao.save(product);

        Product receivedProduct = productDao.getProduct(testId);

        assertEquals(product, receivedProduct);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testSaveNewProductWithNonExistingId() {
        when(product.getId()).thenReturn(33L);
        productDao.save(product);
    }

    // delete method tests

    @Test
    public void testDeleteProductWithExistingId() {
        Long testId = 777L;

        when(product.getId()).thenReturn(null);
        when(product.getStock()).thenReturn(10);
        when(product.getPrice()).thenReturn(new BigDecimal(300));

        int initialSize = productDao.findProducts(null,null,null).size();

        productDao.save(product);
        int sizeAfterSaving = productDao.findProducts(null,null,null).size();

        when(product.getId()).thenReturn(testId);

        assertEquals(1, sizeAfterSaving - initialSize);

        productDao.delete(testId);

        int finalSize = productDao.findProducts(null,null,null).size();

        assertEquals(initialSize, finalSize);
    }

    @Test
    public void testDeleteProductWithNonExistingId() {
        int size = productDao.findProducts(null, null, null).size();
        productDao.delete(123L);
        assertEquals(size, productDao.findProducts(null, null, null).size());
    }

    // findProducts method tests

    @Test
    public void testFindProductsNonNullPrice() {
        int initialSize = productDao.findProducts(null, null, null).size();

        when(product.getId()).thenReturn(null);
        when(product.getStock()).thenReturn(10);
        when(product.getPrice()).thenReturn(null);

        productDao.save(product);

        int finalSize = productDao.findProducts(null, null, null).size();

        assertEquals(initialSize, finalSize);
    }

    @Test
    public void testFindProductsNonNullStock() {
        int initialSize = productDao.findProducts(null, null, null).size();

        when(product.getId()).thenReturn(null);
        when(product.getStock()).thenReturn(0);
        when(product.getPrice()).thenReturn(new BigDecimal(300));

        productDao.save(product);

        int finalSize = productDao.findProducts(null, null, null).size();

        assertEquals(initialSize, finalSize);
    }

    @Test
    public void testFindProductsByQuery() {
        String query = "apple 8";

        productDao.delete(0L);

        when(product.getId()).thenReturn(null);
        when(product.getStock()).thenReturn(10);
        when(product.getPrice()).thenReturn(new BigDecimal(300));
        when(product.getDescription()).thenReturn("Apple iPhone 8 max");

        productDao.save(product);

        when(product.getId()).thenReturn((long) (numberOfSampleProducts + 1));

        Product receivedProduct = productDao.findProducts(query, null, null).get(0);

        assertEquals(product, receivedProduct);
    }

    @Test
    public void testFindProductsSortedByDescriptionAscOrder() {
        productDao.delete(0L);

        when(product.getId()).thenReturn(null);
        when(product.getStock()).thenReturn(10);
        when(product.getPrice()).thenReturn(new BigDecimal(300));
        when(product.getDescription()).thenReturn("AAA");

        productDao.save(product);

        Product receivedProduct = productDao.findProducts(null,
                SortField.description, SortOrder.asc).get(0);

        assertEquals(product, receivedProduct);
    }

    @Test
    public void testFindProductsSortedByPriceDescOrder() {
        productDao.delete(13L);

        when(product.getId()).thenReturn(null);
        when(product.getStock()).thenReturn(10);
        when(product.getPrice()).thenReturn(new BigDecimal(99999));

        productDao.save(product);

        Product receivedProduct = productDao.findProducts(null,
                SortField.price, SortOrder.desc).get(0);

        assertEquals(product, receivedProduct);
    }
}
