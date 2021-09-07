package com.es.phoneshop.model.product;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    Currency usd ;
    Product testProduct;
    Product updatedTestProduct;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        testProduct=new Product("test", "test", new BigDecimal(150), usd, 40, "test");
        updatedTestProduct=new Product(13L,"test1", "test1", new BigDecimal(150), usd, 40, "test1");
        usd = Currency.getInstance("USD");
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void getProduct() {
        try {
            assertEquals(productDao.getProduct(0L),
                    new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findProducts() {
        assertEquals(productDao.findProducts().size(), 13);
    }

    @Test
    public void save() {
        productDao.save(testProduct);
        assertTrue(productDao.findProducts().contains(testProduct));
    }


    @Rule
    public ExpectedException expectedException=ExpectedException.none();
    @Test
    public void delete() throws ProductNotFoundException {
        productDao.delete(0l);
        expectedException.expect(ProductNotFoundException.class);
        productDao.getProduct(0l);
    }

    @Test
    public void update() throws ProductNotFoundException {
        productDao.save(testProduct);
        productDao.update(updatedTestProduct);
        assertTrue(productDao.getProduct(13l).getCode().equals("test1"));
    }
}
