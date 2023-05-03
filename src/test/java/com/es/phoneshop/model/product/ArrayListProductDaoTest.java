package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private Product productToSave;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        Currency usd = Currency.getInstance("USD");
        productToSave = new Product("test", "test", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
    }

    @Test
    public void whenFindProductsThenReturnNotEmptyProductsArray() {
        assertNotNull(productDao.findProducts());
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void whenFindProductByIncorrectIdThenThrowException() {
        productDao.getProduct(0L);
    }

    @Test
    public void whenFindProductByCorrectIdThenReturnProduct() {
        for (Product product : productDao.findProducts()) {
            long id = product.getId();
            assertNotNull(productDao.getProduct(id));
        }
    }

    @Test
    public void whenFindProductsThenReturnProductsWithNotNullPriceAndPositiveStock() {
        for (Product product : productDao.findProducts()) {
            assertNotNull(product);
            assertNotNull(product.getPrice());
            assertTrue(product.getStock() > 0);
        }
    }

    @Test(expected = RuntimeException.class)
    public void whenProductToSaveIsNullThenThrowException() {
        productDao.save(null);
    }

    @Test
    public void whenSaveProductThenReturnEqualSavedProductToProductBeforeSaving() {
        productDao.save(productToSave);
        Product result = productDao.getProduct(productToSave.getId());
        assertEquals(result, productToSave);
    }

    @Test
    public void whenSaveProductThenProductIdShouldGetValue() {
        productDao.save(productToSave);
        assertNotNull(productToSave.getId());
    }

    @Test(expected = RuntimeException.class)
    public void whenDeleteProductThenShouldThrowExceptionAfterGettingOfDeletedProduct() {
        productDao.delete(1L);
        productDao.getProduct(1L);
    }

    @Test(expected = RuntimeException.class)
    public void whenDeleteProductWithIncorrectIdThenShouldThrowException() {
        productDao.delete(0L);
    }

    @Test
    public void whenProductToSaveHasNotNullExistedIdThenShouldUpdate() throws CloneNotSupportedException {
        Product oldProduct = productDao.getProduct(1L).clone();
        Product newProduct = new Product(1L, "update", "update", new BigDecimal(100), Currency.getInstance("USD"), 5, "xxxxxx");
        productDao.save(newProduct);
        assertNotEquals(oldProduct, newProduct);
        assertEquals(productDao.getProduct(1L), newProduct);
    }
}
