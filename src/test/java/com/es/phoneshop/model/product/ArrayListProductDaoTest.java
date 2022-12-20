package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exceptions.ProductNotFoundException;
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
    public void setup() throws ProductNotFoundException {
        productDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(!productDao.findProducts("3310",SortField.valueOf("price"),SortOrder.valueOf("asc")).isEmpty());
    }

    @Test
    public void testSaveNewProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product=new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        Product result = productDao.getProduct(product.getId());
        assertNotNull(result);
        assertEquals("test-product", result.getCode());
    }
    @Test
    public void testFindProductWithZeroStock() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product=new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        List<Product> testArray = productDao.findProducts("3310",SortField.valueOf("price"),SortOrder.valueOf("asc"));
        assertFalse(testArray.contains(product));
    }
    @Test
    public void testFindProductsWithNullPrice() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product=new Product("test-product", "Samsung Galaxy S", null, usd, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        List<Product> testArray=productDao.findProducts("3310",SortField.valueOf("price"),SortOrder.valueOf("asc"));
        assertFalse(testArray.contains(product));
    }
    @Test
    public void testDeleteProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product=new Product(1L,"test-product", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        try{
            productDao.save(product);
            productDao.delete(1L);
            Product testProduct=productDao.getProduct(1L);
            fail("Expected ProductNotFoundException");
        }catch (ProductNotFoundException e){
            assertEquals(e.getMessage(),new ProductNotFoundException().getMessage());
        }

    }
    @Test
    public void testGetProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product=new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);
        assertEquals(productDao.getProduct(product.getId()),product);
    }
}
