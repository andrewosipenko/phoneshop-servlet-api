package com.es.phoneshop.model.product;

import static org.junit.Assert.*;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.TestableSingletonProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.service.ProductServiceImpl;
import com.es.phoneshop.model.product.sortEnums.SortField;
import com.es.phoneshop.model.product.sortEnums.SortOrder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

public class ProductServiceImplTest {

    private TestableSingletonProductDao<List<Product>> productDao = ArrayListProductDao.getInstance();
    private ProductServiceImpl productService;
    private final List<Product> testList = new ArrayList<>();
    private Product validExample1;
    private Product exampleWithNonPositiveStock;
    private Product validExample2;
    private Product exampleWithNullPrice;
    private Product validExampleWithId;
    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        this.validExampleWithId = new Product( 1L, "test4", "4Samsung Galaxy S", null, usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        this.validExample1 = new Product( "test1", "3Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        this.exampleWithNonPositiveStock = new Product( "test2", "2Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        this.validExample2 = new Product( "test3", "1Samsung Galaxy S III", new BigDecimal(200), usd, 50, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        this.exampleWithNullPrice = new Product( "test4", "0Samsung Galaxy S", null, usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testList.add(validExample1);
        testList.add(exampleWithNonPositiveStock);
        testList.add(validExample2);
        testList.add(exampleWithNullPrice);
        productService = new ProductServiceImpl();
        productDao.dropToDefault();
    }

    @Test
    public void getProductWithLong() {
        productService.save(validExample1);
        assertTrue(validExample1.getId() > 0);
        assertEquals(validExample1,productService.getProduct(validExample1.getId()));
    }

    @Test(expected = NoSuchElementException.class)
    public void getNonExistingProduct(){
        productService.getProduct(Long.MAX_VALUE);
    }

    @Test
    public void findProducts() {
        var listWithPositiveStock = new ArrayList<>();
        listWithPositiveStock.add(validExample1);
        listWithPositiveStock.add(validExample2);
        productService.save(validExample1);
        productService.save(exampleWithNonPositiveStock);
        productService.save(validExample2);
        assertArrayEquals(listWithPositiveStock.toArray(), productService.findProducts().toArray());
    }

    @Test
    public void findProductsWithNonPositiveStock() {
        productService.save(exampleWithNonPositiveStock);
        assertEquals(0, productService.findProducts().size());
    }

    @Test
    public void findProductsWithNullPrice() {
        productService.save(exampleWithNullPrice);
        assertEquals(0, productService.findProducts().size());
    }

    @Test
    public void save() {
        productService.save(validExample1);
        productService.save(validExample2);

        assertEquals(1L, (long) validExample1.getId());
        assertEquals(2L, (long) validExample2.getId());

        assertEquals(validExample1, productService.getProduct(validExample1.getId()));
        assertEquals(validExample2, productService.getProduct(validExample2.getId()));
    }

    @Test(expected = NoSuchElementException.class)
    public void delete() {
        productService.save(validExample1);
        assertEquals(validExample1, productService.getProduct(validExample1.getId()));

        productService.delete(validExample1.getId());
        productService.getProduct(validExample1.getId());
    }

    @Test
    public void getProductWithPathInfo() {
        productDao.set(List.of(validExampleWithId));

        String validPathInfo = "/1/everything";


        assertEquals(validExampleWithId, productService.getProduct(validPathInfo));


    }

    @Test(expected = NoSuchElementException.class)
    public void getProductWithIncorrectPathInfo() {
        productDao.set(List.of(validExampleWithId));
        String notValidPathInfo = "/notNumber/everything";
        productService.getProduct(notValidPathInfo);
    }

    @Test
    public void findProductsWithParams() {
        productDao.set(testList);
        var expectedList = new ArrayList<>(List.of(validExample1, validExample2));
        Collections.reverse(expectedList);
        var actual = productService.findProducts(String.valueOf(SortField.description), String.valueOf(SortOrder.asc), " ");
        assertArrayEquals(expectedList.toArray(), actual.toArray());

        Collections.reverse(expectedList);
        actual = productService.findProducts(String.valueOf(SortField.description), String.valueOf(SortOrder.desc), " ");
        assertArrayEquals(expectedList.toArray(), actual.toArray());
    }
}