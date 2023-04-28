package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.DAO.ArrayListProductDao;
import com.es.phoneshop.model.product.DAO.ProductDao;
import com.es.phoneshop.model.product.entity.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    Currency usd = Currency.getInstance("USD");
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        Product product = productDao.save(new Product("test", "test", new BigDecimal(100), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        assertTrue(product.getId() > 0);
        List<Product> products = productDao.findProducts();
        assertFalse(products.isEmpty());
        assertFalse(products.stream()
                .anyMatch(product1 -> product1.equals(product)));
    }

    @Test
    public void testSaveProduct() {
        Product product = productDao.save(new Product("test", "test", new BigDecimal(100), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));

        assertTrue(product.getId() > 0);
        productDao.save(product);//add the same product to check repeatability
        assertNotNull(productDao.getProduct(product.getId()));
        assertEquals("test", product.getCode());
    }

    @Test
    public void testDeleteProduct() {
        Product product = productDao.save(new Product("test", "test", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        assertTrue(product.getId() > 0);

        List<Product> products = productDao.findProducts();
        assertFalse(products.isEmpty());
        assertTrue(products.stream()
                .anyMatch(product1 -> product1.equals(product)));
        productDao.delete(product.getId());
        assertFalse(productDao.findProducts().stream()
                .anyMatch(product1 -> product1.equals(product)));
    }
}
