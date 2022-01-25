package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Product defaultProduct;
    private final Currency USD = Currency.getInstance("USD");

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        defaultProduct = new Product(
                "sgs",
                "Samsung Galaxy S",
                new BigDecimal(100),
                USD,
                100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"
        );
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindSampleProducts() {
        ((ArrayListProductDao) productDao).setSampleProducts();
        boolean isSampleProductsListEmpty =
                productDao.findProducts().isEmpty();
        assertFalse(isSampleProductsListEmpty);
    }

    @Test
    public void testNotFindProductsWithZeroPrice() {
        defaultProduct.setPrice(BigDecimal.ZERO);
        productDao.save(defaultProduct);
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testNotFindProductsWithZeroStock() {
        int zeroStock = 0;
        int negativeStock = -1;
        productDao.save(defaultProduct);
        defaultProduct.setStock(zeroStock);
        assertTrue(productDao.findProducts().isEmpty());
        defaultProduct.setStock(negativeStock);
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testNotFindProductsWithNonPositiveStock() {
        int zeroStock = 0;
        defaultProduct.setStock(zeroStock);
        productDao.save(defaultProduct);
        boolean isProductNotFound =
                productDao.findProducts().isEmpty();
        assertTrue(isProductNotFound);
    }

    @Test
    public void testCanNotSaveProductWithNegativeStock() {
        int negativeStock = -1;
        defaultProduct.setStock(negativeStock);
        exceptionRule.expect(InvalidProductException.class);
        productDao.save(defaultProduct);
    }

    @Test
    public void testGetProductWithExistingId() {
        long existingId = 1L;
        productDao.save(defaultProduct);
        assertNotNull(productDao.getProduct(existingId));
    }

    @Test
    public void testGetProductWithNonExistingId() {
        long nonExistingId = -1L;
        exceptionRule.expect(NoSuchElementException.class);
        productDao.getProduct(nonExistingId);
    }

    @Test
    public void testSaveProductWithExistingId() {
        ((ArrayListProductDao) productDao).setSampleProducts();
        long existingId = 1L;
        defaultProduct.setId(existingId);
        productDao.save(defaultProduct);
        assertNotSame(defaultProduct, productDao.getProduct(existingId));
        assertEquals((long) defaultProduct.getId(), existingId);
    }

    @Test
    public void testSaveProductWithNonExistingId() {
        long existingId = 1L;
        defaultProduct.setId(existingId);
        productDao.save(defaultProduct);
        assertSame(defaultProduct, productDao.getProduct(existingId));
        assertEquals((long) defaultProduct.getId(), existingId);
    }

    @Test
    public void testSaveProductWithoutId() {
        productDao.save(defaultProduct);
        long id = defaultProduct.getId();
        assertSame(defaultProduct, productDao.getProduct(id));
    }

    @Test
    public void testDeleteProductWithExistingId() {
        productDao.save(defaultProduct);
        long id = defaultProduct.getId();
        assertNotNull(productDao.getProduct(id));
        productDao.delete(id);
        assertTrue(productDao.findProducts().isEmpty());
        exceptionRule.expect(NoSuchElementException.class);
        assertNull(productDao.getProduct(id));
    }

    @Test
    public void testDeleteProductWithNonExistingId() {
        productDao.save(defaultProduct);
        long id = defaultProduct.getId();
        long fakeId = -1L;
        assertNotNull(productDao.getProduct(id));
        int sizeBeforeDeleting = productDao.findProducts().size();
        productDao.delete(fakeId);
        assertNotNull(productDao.getProduct(id));
        int sizeAfterDeleting = productDao.findProducts().size();
        assertEquals(sizeBeforeDeleting, sizeAfterDeleting);
    }
}
