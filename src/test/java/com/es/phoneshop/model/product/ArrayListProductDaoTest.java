package com.es.phoneshop.model.product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsResults() {
        assertTrue(!productDao.findProducts().isEmpty());
    }

    @Test
    public void testDeleteProduct() {
        List<Product> products = productDao.findProducts();
        productDao.delete(productDao.findProducts().get(0).getId());
        products.remove(0);
        Assert.assertEquals(productDao.findProducts(), products);
    }


    @Test(expected = RuntimeException.class)
    public void testDeleteProductException() {
        Long id = productDao.findProducts().get(0).getId();
        productDao.delete(productDao.findProducts().get(0).getId());
        productDao.delete(id);
    }

    @Test
    public void testGetProduct() {
        Product p = productDao.findProducts().get(0);
        Product product = productDao.getProduct(productDao.findProducts().get(0).getId());
        assertEquals(p, product);
    }

    @Rule
    public ExpectedException exc = ExpectedException.none();

    @Test
    public void testGetProductException() {
        exc.expect(NoSuchElementException.class);
        Long id = productDao.findProducts().get(0).getId();
        productDao.delete(productDao.findProducts().get(0).getId());
        productDao.getProduct(id);

    }

    @Test
    public void testSafe() {
        List<Product> products = productDao.findProducts();
        Product p = productDao.findProducts().get(productDao.findProducts().size()-1);
        productDao.delete(p.getId());
        productDao.save(p);
        assertEquals(products, productDao.findProducts());
    }

    @Test(expected = RuntimeException.class)
    public void testSaveException() {
        productDao.save(productDao.findProducts().get(0));

    }
}
