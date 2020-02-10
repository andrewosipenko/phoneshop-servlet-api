package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProductIsTrue() {
        assertTrue(productDao.getProduct(1L).equals(new Product(1L, "sgs",
                "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"))
        );
    }

    @Test
    public void testGetProductIsFalse() {
        assertTrue(productDao.getProduct(40L) == null);
    }

    @Test
    public void saveProductToList() {
        Product product = new Product(15L, "sgs",
                "Samsung Galaxy Sss", new BigDecimal(100), Currency.getInstance("USD"), 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);
        assertTrue(productDao.getProduct(15L).equals(product));
    }

    @Test
    public void deleteProductFromCenter() {
        Product product = new Product(20L, "simc61",
                "Siemens C61", new BigDecimal(80), Currency.getInstance("USD"), 30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg");
        productDao.save(product);
        if(productDao.getProduct(20L).equals(product)) {
            productDao.delete(20L);
            assertTrue(productDao.getProduct(20L) == null);
        }
    }
}
