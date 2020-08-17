package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.TestableSingletonProductDao;
import com.es.phoneshop.model.product.entity.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private TestableSingletonProductDao<List<Product>> productDao;
    private final List<Product> testList = new ArrayList<>();
    private Product example;
    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        this.example = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testList.add(example);
        testList.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testList.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNoResults() {
        productDao.set(testList);
        assertFalse(productDao.get(Long.MAX_VALUE).isPresent());
        productDao.get(Long.MAX_VALUE).get();
    }

    @Test
    public void get() {
        productDao.set(testList);
        assertEquals(Optional.of(example), productDao.get(example.getId()));
        assertNotEquals(Optional.of(example), productDao.get(2L));
    }

    @Test
    public void getAll() {
        productDao.set(testList);
        var result = new ArrayList<>(productDao.getAll());
        assertArrayEquals(result.toArray(), testList.toArray());
    }

    @Test
    public void save() {
        Product saveExample = new Product(4L, "test-product", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        List<Product> extendsList = new ArrayList<>();
        List<Product> actualList = new ArrayList<>(extendsList);

        productDao.set(actualList);

        extendsList.add(saveExample);
        extendsList.add(example);

        productDao.save(saveExample);
        productDao.save(example);

        assertArrayEquals(extendsList.toArray(), actualList.toArray());
    }

    @Test
    public void saveExistingProduct(){
        List<Product> exampleList = new ArrayList<>();
        List<Product> exampleList2 = new ArrayList<>();
        productDao.set(exampleList);
        assertEquals(0, exampleList.size());
        for (int i = 0; i < 3; i++) {
            productDao.save(example);
        }
        exampleList2.add(example);
        assertArrayEquals(exampleList.toArray(), exampleList2.toArray());
    }

    @Test
    public void delete() {
        productDao.set(testList);
        List<Product> copyList = new ArrayList<>(testList);

        assertEquals(Optional.of(example), productDao.get(1L));
        productDao.delete(1L);
        copyList.remove(example);
        assertArrayEquals(copyList.toArray(), testList.toArray());
    }

    @Test
    public void find() {
        productDao.set(testList);
        List<Product> expectedList = new ArrayList<>(testList);

        assertArrayEquals(expectedList.toArray(), productDao.find("s").toArray());
        expectedList.remove(example);
        assertArrayEquals(expectedList.toArray(), productDao.find("ii").toArray());

    }

    @Test
    public void findNullOrEmptyString() {
        productDao.set(testList);
        List<Product> expectedList = new ArrayList<>(testList);

        assertArrayEquals(expectedList.toArray(), productDao.find(null).toArray());
        assertArrayEquals(expectedList.toArray(), productDao.find(" ").toArray());
    }
}
