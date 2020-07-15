package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private final List<Product> testList = new ArrayList<>();
    private Product example;
    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        this.example = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testList.add(example);
        testList.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testList.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao = new ArrayListProductDao();
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNoResults() {
        productDao.get(Long.MAX_VALUE);
    }

    @Test
    public void get() {
        productDao = new ArrayListProductDao(testList);
        assertEquals(example, productDao.get(example.getId()));
        assertNotEquals(example, productDao.get(2L));
    }

    @Test
    public void getAll() {
        productDao = new ArrayListProductDao(testList);
        var result = new ArrayList<>(productDao.getAll());
        assertArrayEquals(result.toArray(), testList.toArray());
    }

    @Test
    public void save() {
        Product saveExample = new Product(4L, "test-product", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        productDao.save(saveExample);
        assertEquals(saveExample, productDao.get(4L));
    }

    @Test
    public void saveExistingProduct(){
        assertEquals(0, productDao.getAll().size());
        for (int i = 0; i < 3; i++) {
            productDao.save(example);
        }
        assertEquals(1, productDao.getAll().size());
    }

    @Test(expected = NoSuchElementException.class)
    public void delete() {
        productDao = new ArrayListProductDao(testList);
        assertEquals(example, productDao.get(1L));
        productDao.delete(1L);
        productDao.get(1L);
    }
}
