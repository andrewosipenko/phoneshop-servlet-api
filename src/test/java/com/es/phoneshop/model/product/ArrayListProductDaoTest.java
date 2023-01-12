package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts("Honor", SortField.description, SortOrder.asc).isEmpty());
    }

    @Test
    public void testSaveNewProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-product", "Samsung Galaxy S I", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertTrue(product.getId() > 0);
        Product result = productDao.getProduct(Long.valueOf(product.getId()));
        assertNotNull(result);
        assertEquals("test-product", result.getCode());
    }

    @Test
    public void testFindProductWithZeroStock() {
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("test-product", "Samsung Galaxy S I", new BigDecimal(100), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));

        assertTrue(productDao.findProducts("Samsung Galaxy S I", SortField.description, SortOrder.asc).stream().
                noneMatch(product -> product.getStock() == 0));
    }

    @Test
    public void testFindProductWithZeroPrice() {
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("test-product", "Samsung Galaxy S I", new BigDecimal(0), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));

        assertTrue(productDao.findProducts("Samsung Galaxy S I", SortField.description, SortOrder.asc).stream().
                noneMatch(product -> product.getPrice() == null));
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNewProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertTrue(product.getId() >= 0);
        Product result = productDao.getProduct(Long.valueOf(product.getId()));
        assertNotNull(result);
        assertEquals("test", result.getCode());

        productDao.delete(product.getId());
        assertNull(productDao.getProduct(Long.valueOf(product.getId())));
    }

    @Test
    public void testFindProductByDescription() {
        String query = "Apple";
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        productDao.save(product);
        assertEquals(productDao.findProducts(query, SortField.description, SortOrder.asc).get(0), product);
    }

    @Test
    public void testFindProductsSortedByDescriptionAsc() {
        Currency usd = Currency.getInstance("USD");
        List<Product> products = new ArrayList<>();
        products.add(new Product("iphone", "AA", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        products.add(new Product("iphone", "AAA", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        products.add(new Product("iphone", "A", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));

        List<String> sortedProducts = new ArrayList<>();
        sortedProducts.add("A");
        sortedProducts.add("AA");
        sortedProducts.add("AAA");

        for (int i = 0; i < products.size(); i++) {
            productDao.save(products.get(i));
        }
        for (int i = 0; i < products.size(); i++) {
            assertEquals(productDao.findProducts("", SortField.description, SortOrder.asc).get(i).getDescription(), sortedProducts.get(i));
        }
    }
}