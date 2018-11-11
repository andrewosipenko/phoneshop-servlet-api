package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private final Currency usd = Currency.getInstance("USD");
    private final Product product = new Product(1L, "sgs", "Samsung Galaxy S II", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

    @Before
    public void setup() {
        List<Product> testListOfProducts = new ArrayList<>();

        Product product = new Product(1L, "sgs", "Samsung Galaxy S II", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testListOfProducts.add(product);
        testListOfProducts.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        testListOfProducts.add(new Product(3L, "sgs3", "Samsung Galaxy S III", null, usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao = new ArrayListProductDao(testListOfProducts);
    }

    @Test
    public void testFindProduct() {
        assertTrue(productDao.findProducts().get(0).equals(product));
    }


}
