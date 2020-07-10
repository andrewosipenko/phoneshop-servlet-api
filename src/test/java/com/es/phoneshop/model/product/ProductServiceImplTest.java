package com.es.phoneshop.model.product;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductServiceImplTest {
    private ProductServiceImpl productService;
    private final List<Product> testList = new ArrayList<>();
    private Product validExample1;
    private Product exampleWithNonPositiveStock;
    private Product validExample2;
    private Product exampleWithNullPrice;
    private final Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        this.validExample1 = new Product( "test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        this.exampleWithNonPositiveStock = new Product( "test2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        this.validExample2 = new Product( "test3", "Samsung Galaxy S III", new BigDecimal(200), usd, 50, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        this.exampleWithNullPrice = new Product( "test4", "Samsung Galaxy S", null, usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testList.add(validExample1);
        testList.add(exampleWithNonPositiveStock);
        testList.add(validExample2);
        testList.add(exampleWithNullPrice);
        productService = new ProductServiceImpl();
    }

    @Test
    public void getProduct() {
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
}