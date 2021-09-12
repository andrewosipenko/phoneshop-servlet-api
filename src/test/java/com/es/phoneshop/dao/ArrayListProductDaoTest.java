package com.es.phoneshop.dao;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.web.DemoDataServletContextListener;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        if (productDao.findProducts(null, null, null).isEmpty()) {
            DemoDataServletContextListener listener = new DemoDataServletContextListener();
            listener.getSampleProducts().forEach(productDao::save);
        }
    }

    @Test
    public void testGetInstance() {
        ProductDao pd = ArrayListProductDao.getInstance();
        assertSame(productDao, pd);
    }

    @Test
    public void testFindProductsNoResults() {
        List<Product> prods = productDao.findProducts(null, null, null);
        assertFalse(prods.isEmpty());
    }

    @Test
    public void testFindProductsByQuery() {
        String query = "Apple Phone";
        List<Product> products = productDao.findProducts(query, null, null);
        assertEquals(2, products.size());
        assertTrue(products.get(0).getCode().equals("iphone") && products.get(1).getCode().equals("iphone6"));
    }

    @Test
    public void testFindProductsSortedByDescriptionAsc() {
        List<Product> products = productDao.findProducts(null, "description", "asc");
        List<String> codes = Arrays.asList("Apple iPhone", "Apple iPhone 6", "HTC EVO Shift 4G", "Nokia 3310", "Palm Pixi",
                "Samsung Galaxy S", "Samsung Galaxy S III", "Siemens C56", "Siemens C61", "Siemens SXG75",
                "Sony Ericsson C901", "Sony Xperia XZ");
        assertEquals(products.size(), codes.size());
        boolean b = false;
        for (int i = 0; i < products.size(); i++) {
            b = codes.get(i).equals(products.get(i).getDescription());
        }
        assertTrue(b);
    }

    @Test
    public void testFindProductsSortedByDescriptionDesc() {
        List<Product> products = productDao.findProducts(null, "description", "desc");
        List<String> codes = Arrays.asList("Apple iPhone", "Apple iPhone 6", "HTC EVO Shift 4G", "Nokia 3310", "Palm Pixi",
                "Samsung Galaxy S", "Samsung Galaxy S III", "Siemens C56", "Siemens C61", "Siemens SXG75",
                "Sony Ericsson C901", "Sony Xperia XZ");
        assertEquals(products.size(), codes.size());
        boolean b = false;
        for (int i = 0; i < products.size(); i++) {
            b = codes.get(codes.size() - 1 - i).equals(products.get(i).getDescription());
        }
        assertTrue(b);
    }

    @Test
    public void testFindProductsSortedByPriceAsc() {
        List<Product> products = productDao.findProducts(null, "price", "asc");
        List<String> codes = Arrays.asList("Nokia 3310", "Siemens C56", "Siemens C61", "Samsung Galaxy S", "Sony Xperia XZ",
                "Siemens SXG75", "Palm Pixi", "Apple iPhone", "Samsung Galaxy S III", "HTC EVO Shift 4G",
                "Sony Ericsson C901", "Apple iPhone 6");
        assertEquals(products.size(), codes.size());
        boolean b = false;
        for (int i = 0; i < products.size(); i++) {
            b = codes.get(i).equals(products.get(i).getDescription());
        }
        assertTrue(b);
    }

    @Test
    public void testFindProductsSortedByPriceDesc() {
        List<Product> products = productDao.findProducts(null, "price", "desc");
        List<String> codes = Arrays.asList("Siemens C56","Nokia 3310",  "Siemens C61", "Samsung Galaxy S", "Sony Xperia XZ",
                "Siemens SXG75", "Palm Pixi", "Apple iPhone", "Samsung Galaxy S III", "HTC EVO Shift 4G",
                "Sony Ericsson C901", "Apple iPhone 6");
        assertEquals(products.size(), codes.size());
        boolean b = false;
        for (int i = 0; i < products.size(); i++) {
            b = codes.get(codes.size() - 1 - i).equals(products.get(i).getDescription());
        }
        assertTrue(b);
    }

    @Test
    public void testGetProductWithNullId() {
        Optional<Product> result=productDao.getProduct(null);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSaveNewProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertTrue(product.getId() > 0);
        Optional<Product> result = productDao.getProduct(product.getId());
        assertTrue(result.isPresent());
        assertEquals("sgs1", result.get().getCode());
    }

    @Test
    public void testSaveNewProductZeroStock() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("zero-stock-test", "Samsung Galaxy S", new BigDecimal(100), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertFalse(productDao.getProduct(product.getId()).isPresent());
    }

    @Test
    public void testDeleteNewProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("deleted-product", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        productDao.delete(product.getId());
        assertFalse(productDao.getProduct(product.getId()).isPresent());
    }
}
