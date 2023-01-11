package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.entity.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void testFindProductsSomeResults() {
        assertFalse(productDao.findProducts("",null,null).isEmpty());
    }

    @Test
    public void testSaveNewProduct() {
        Currency usd=Currency.getInstance("USD");
        Product newProduct=new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(newProduct);

        assertTrue(newProduct.getId()>0);

        Product testProduct=productDao.getProduct(newProduct.getId());
        assertEquals(testProduct,newProduct);
    }

    @Test
    public void testGettingProduct() {
        Product testProduct=productDao.getProduct(4L);
        assertNotNull(testProduct);
    }

    @Test
    public void testUpdateProduct() {
        Product productBeforeUpdating=productDao.getProduct(3L);
        Product productAfterUpdating=productDao.getProduct(3L);
        productAfterUpdating.setCode("1234");

        productDao.save(productAfterUpdating);

        assertEquals(productAfterUpdating.getId(),productBeforeUpdating.getId());
    }

    @Test
    public void testDeleteProduct() {
        Product fifthProduct=productDao.getProduct(5L);
        productDao.delete(5L);
        Product newFifthProduct=productDao.getProduct(5L);

        assertNotEquals(newFifthProduct,fifthProduct);
    }

    @Test
    public void testNotShowingProductWithZeroStock() {
        Currency usd=Currency.getInstance("USD");
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));

        List<Product> testList=productDao.findProducts("",null,null);
        assertTrue(testList.stream().
                noneMatch(product -> product.getStock() == 0));
    }

    @Test
    public void testNotShowingProductWithNullPrice() {
        Currency usd=Currency.getInstance("USD");
        productDao.save(new Product("sgs", "Samsung Galaxy S", null, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));

        List<Product> testList=productDao.findProducts("",null,null);
        assertTrue(testList.stream().
                noneMatch(product -> product.getPrice() == null));
    }
}
