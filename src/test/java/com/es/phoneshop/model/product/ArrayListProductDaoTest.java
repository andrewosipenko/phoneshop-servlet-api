package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.enums.sort.SortField;
import com.es.phoneshop.model.product.enums.sort.SortOrder;
import com.es.phoneshop.model.product.exceptions.ProductNotFindException;
import com.es.phoneshop.model.product.productdao.ArrayListProductDao;
import com.es.phoneshop.model.product.productdao.PriceHistory;
import com.es.phoneshop.model.product.productdao.Product;
import com.es.phoneshop.model.product.productdao.ProductDao;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() throws IllegalAccessException, NoSuchFieldException {
        productDao = ArrayListProductDao.getInstance();
        Field instance = ArrayListProductDao.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        Currency usd = Currency.getInstance("USD");
        PriceHistory time1 = new PriceHistory(LocalDate.of(2015, 3, 2), new BigDecimal(50));
        PriceHistory time2 = new PriceHistory(LocalDate.of(2017, 3, 2), new BigDecimal(100));
        List<PriceHistory> priceHistoryList = new ArrayList<>();
        priceHistoryList.add(time1);
        priceHistoryList.add(time2);
        productDao.saveProduct(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", priceHistoryList));
        productDao.saveProduct(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg", priceHistoryList));
        productDao.saveProduct(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", null));
        productDao.saveProduct(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg", priceHistoryList));
        productDao.saveProduct(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg", priceHistoryList));
        productDao.saveProduct(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg", priceHistoryList));
        productDao.saveProduct(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg", null));
        productDao.saveProduct(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg", priceHistoryList));
        productDao.saveProduct(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg", priceHistoryList));
        productDao.saveProduct(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg", priceHistoryList));
        productDao.saveProduct(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg", null));
        productDao.saveProduct(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg", priceHistoryList));
        productDao.saveProduct(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg", priceHistoryList));
    }


    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testFindProductsWithIncorrectConfigurationStock() {
        Product testProduct = new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, -10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.saveProduct(testProduct);
        List<Product> productList = productDao.findProducts(null, null, null);
        if (productList.contains(testProduct)) {
            fail("Must not be testProduct in productList");
        }
    }

    @Test
    public void testFindProductsWithIncorrectConfigurationPrice() {
        Product testProduct = new Product(0L, "sgs", "Samsung Galaxy S", null, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.saveProduct(testProduct);
        List<Product> productList = productDao.findProducts(null, null, null);
        if (productList.contains(testProduct)) {
            fail("Must not be testProduct in productList");
        }
    }

    @Test
    public void testFindProductSortPartDescriptionAsc(){
        Product p1 = new Product(0L,"sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product p3 = new Product(2L,"sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        Product p4 = new Product(3L,"iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        Product p5 = new Product(4L,"iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg");
        Product p6 = new Product(5L,"htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        Product p7 = new Product(6L,"sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg");
        Product p8 = new Product(7L,"xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg");
        Product p9 = new Product(8L,"nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg");
        Product p10 = new Product(9L,"palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg");
        Product p11 = new Product(10L,"simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");
        Product p12 = new Product(11L,"simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg");
        Product p13 = new Product(12L,"simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        List<Product> sortedList = new ArrayList<>();
        sortedList.add(p1);
        sortedList.add(p3);
        sortedList.add(p4);
        sortedList.add(p5);
        sortedList.add(p6);
        sortedList.add(p7);
        sortedList.add(p8);
        sortedList.add(p9);
        sortedList.add(p10);
        sortedList.add(p11);
        sortedList.add(p12);
        sortedList.add(p13);
        sortedList = sortedList.stream().sorted(Comparator.comparing(Product::getDescription)).collect(Collectors.toList());

        assertEquals(productDao.findProducts(null, SortField.DESCRIPTION, SortOrder.ASCENDING), sortedList);
    }

    @Test
    public void testGetProduct() {
        Product testProduct = new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        assertEquals(testProduct, productDao.getProduct(0L));
    }

    @Test
    public void testDeleteProductThatExist() {
        long id = 3L;
        productDao.deleteProduct(id);
        try {
            productDao.getProduct(id);
            fail("Expected ProductNotFindException");
        } catch (ProductNotFindException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testDeleteProductThatNotExist() {
        long id = -1L;
        try {
            productDao.deleteProduct(id);
            fail("Expected NoSuchElementException");
        } catch (ProductNotFindException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testSaveProductWithId() {
        Product testProduct = new Product(3L, "testProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.saveProduct(testProduct);
        assertEquals(testProduct, productDao.getProduct(3L));
    }

    @Test
    public void testSaveProductWithoutId() {
        Product testProduct = new Product("testProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.saveProduct(testProduct);
        assertEquals(testProduct, productDao.getProduct(testProduct.getId()));
    }

    @Test
    public void testSaveProductWithIdOutOfRange() {
        Product testProduct = new Product(-1L, "testProduct", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.saveProduct(testProduct);
        assertEquals(testProduct, productDao.getProduct(testProduct.getId()));
    }
}
