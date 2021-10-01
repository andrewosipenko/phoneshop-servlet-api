package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exceptions.ItemNotFindException;
import com.es.phoneshop.model.product.productdao.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private final Currency usd = Currency.getInstance("USD");
    List<PriceHistory> priceHistoryList;

    @Before
    public void setup() throws IllegalAccessException, NoSuchFieldException {
        productDao = ArrayListProductDao.getInstance();
        Field instance = ArrayListProductDao.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        Currency usd = Currency.getInstance("USD");
        PriceHistory time1 = new PriceHistory(LocalDate.of(2015, 3, 2), new BigDecimal(50));
        PriceHistory time2 = new PriceHistory(LocalDate.of(2017, 3, 2), new BigDecimal(100));
        priceHistoryList = new ArrayList<>();
        priceHistoryList.add(time1);
        priceHistoryList.add(time2);
        productDao.saveProduct(new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(2L).setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(3L).setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(4L).setCode("iphone6").setDescription("Apple iPhone 6").setPrice(new BigDecimal(1000)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(5L).setCode("htces4g").setDescription("HTC EVO Shift 4G").setPrice(new BigDecimal(320)).setCurrency(usd).setStock(3).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(6L).setCode("sec901").setDescription("Sony Ericsson C901").setPrice(new BigDecimal(420)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(7L).setCode("xperiaxz").setDescription("Sony Xperia XZ").setPrice(new BigDecimal(120)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(8L).setCode("nokia3310").setDescription("Nokia 3310").setPrice(new BigDecimal(70)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(9L).setCode("palmp").setDescription("Palm Pixi").setPrice(new BigDecimal(170)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(10L).setCode("simc56").setDescription("Siemens C56").setPrice(new BigDecimal(70)).setCurrency(usd).setStock(20).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(11L).setCode("simc61").setDescription("Siemens C61").setPrice(new BigDecimal(80)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(12L).setCode("simsxg75").setDescription("Siemens SXG75").setPrice(new BigDecimal(150)).setCurrency(usd).setStock(40).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg").setPriceHistory(priceHistoryList).build());
    }


    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testFindProductsWithIncorrectConfigurationStock() {
        Product testProduct = new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(0).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
        productDao.saveProduct(testProduct);
        List<Product> productList = productDao.findProducts(null, null, null);
        if (productList.contains(testProduct)) {
            fail("Must not be testProduct in productList");
        }
    }

    @Test
    public void testFindProductsWithIncorrectConfigurationPrice() {
        Product testProduct = new ProductBuilderImpl().setCode("test").setDescription("Samsung Galaxy S").setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
        productDao.saveProduct(testProduct);
        List<Product> productList = productDao.findProducts(null, null, null);
        if (productList.contains(testProduct)) {
            fail("Must not be testProduct in productList");
        }
    }

    @Test
    public void testGetProduct() {
        Product test = new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
        assertEquals(test, productDao.getProduct(0L));
    }

    @Test
    public void testDeleteProductThatExist() {
        long id = 3L;
        productDao.deleteProduct(id);
        try {
            productDao.getProduct(id);
            fail("Expected ProductNotFindException");
        } catch (ItemNotFindException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testDeleteProductThatNotExist() {
        long id = -1L;
        try {
            productDao.deleteProduct(id);
            fail("Expected NoSuchElementException");
        } catch (ItemNotFindException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testSaveProductWithId() {
        Product testProduct = new ProductBuilderImpl().setId(3L).setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").setPriceHistory(priceHistoryList).build();
        productDao.saveProduct(testProduct);
        assertEquals(testProduct, productDao.getProduct(3L));
    }

    @Test
    public void testSaveProductWithoutId() {
        Product testProduct = new ProductBuilderImpl().setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").setPriceHistory(priceHistoryList).build();
        productDao.saveProduct(testProduct);
        assertEquals(testProduct, productDao.getProduct(testProduct.getId()));
    }

    @Test
    public void testSaveProductWithIdOutOfRange() {
        Product testProduct = new ProductBuilderImpl().setId(-3L).setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").setPriceHistory(priceHistoryList).build();
        productDao.saveProduct(testProduct);
        assertEquals(testProduct, productDao.getProduct(testProduct.getId()));
    }
}