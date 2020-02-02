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
        assertTrue(!productDao.findProducts().isEmpty());
    }

    @Test
    public void testNullPrice() {
        assertFalse(productDao.findProducts().stream()
                .filter(product -> product.getPrice() == null)
                .findAny()
                .isPresent()
        );
    }

    @Test
    public void testGetProduct() {
        assertTrue(productDao.getProduct(4L) != null);
    }

    @Test
    public void testSaveProduct() {
        Product prod = new Product(14L, "nokia111", "Nokia 355", new BigDecimal(50), Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(prod);
        assertTrue(productDao.getProduct(prod.getId()) == prod);
    }

}
