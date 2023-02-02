package com.es.phoneshop.model.product;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enumeration.SortField;
import com.es.phoneshop.enumeration.SortOrder;
import com.es.phoneshop.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        this.productDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void testSaveNewProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertTrue(product.getId() > 0);
        Product result = productDao.findById(product.getId());
        assertNotNull(result);
        assertEquals("test", result.getCode());
    }

    @Test
    public void testSaveNewProductCorrectIdSet() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 = new Product("test2", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product1);
        productDao.save(product2);

        assertEquals(1L, product2.getId() - product1.getId());
    }

    @Test
    public void testSaveExistedProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product productBeforeChanges = new Product("test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(productBeforeChanges);

        Product productAfterChanges = new Product("test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productAfterChanges.setId(productBeforeChanges.getId());
        productAfterChanges.setCode("test2");

        productDao.save(productAfterChanges);

        assertEquals(productDao.findById(productBeforeChanges.getId()), productAfterChanges);
    }

    @Test
    public void testSaveExistedProductPriceHistoryUpdates() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product productBeforeChanges = new Product("test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(productBeforeChanges);

        Product productAfterChanges = new Product("test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productAfterChanges.setId(productBeforeChanges.getId());
        productAfterChanges.setPrice(new BigDecimal(200));

        productDao.save(productAfterChanges);

        List<BigDecimal> actualPriceHistoryList = productAfterChanges
                .getPriceHistoryList()
                .stream()
                .map(priceHistory -> priceHistory.getPrice())
                .collect(Collectors.toList());

        List<BigDecimal> expectedPriceHistoryList = new ArrayList<>();
        expectedPriceHistoryList.add(productBeforeChanges.getPrice());
        expectedPriceHistoryList.add(productAfterChanges.getPrice());

        assertEquals(expectedPriceHistoryList, actualPriceHistoryList);
    }

    @Test
    public void testGetProductCorrectId() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertEquals(product, productDao.findById(product.getId()));
    }


    @Test(expected = ProductNotFoundException.class)
    public void testGetProductIncorrectId() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        productDao.findById(product.getId() + 1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductNullId() throws ProductNotFoundException {
        productDao.findById(null);
    }

    @Test
    public void testFindProductsWithCorrectConditions() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        assertTrue(productDao.findProductsByQueryAndSortParameters(null, null, null).contains(product));
    }

    @Test
    public void testFindProductsWithIncorrectPrice() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", null, usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        assertFalse(productDao.findProductsByQueryAndSortParameters(null, null, null).contains(product));
    }

    @Test
    public void testFindProductsWithIncorrectStock() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, -5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        assertFalse(productDao.findProductsByQueryAndSortParameters(null, null, null).contains(product));
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNullIdProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.delete(product.getId());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNonExistedProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        productDao.delete(product.getId() + 1L);
    }

    @Test
    public void testDeleteExistedProduct() throws ProductNotFoundException {
        List<Product> productsBeforeDeleting = productDao.findProductsByQueryAndSortParameters(null, null, null);

        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        productDao.delete(product.getId());
        List<Product> productsAfterDeleting = productDao.findProductsByQueryAndSortParameters(null, null, null);

        assertTrue(productsBeforeDeleting.equals(productsAfterDeleting));
    }

    @Test
    public void testFindProductsNullSortFieldAndNullString() {
        List<Product> actualProductsOrder = productDao.findProductsByQueryAndSortParameters(null, null, null);
        List<Product> expectedProductsOrder = actualProductsOrder.stream().sorted(Comparator.comparing(
                product -> product.getPrice()
        )).collect(Collectors.toList());

        assertEquals(actualProductsOrder, expectedProductsOrder);
    }

    @Test
    public void testFindProductsNullSortFieldAndStringOfSpaces() {
        List<Product> actualProductsOrder = productDao.findProductsByQueryAndSortParameters("   ", null, null);
        List<Product> expectedProductsOrder = actualProductsOrder.stream().sorted(Comparator.comparing(
                product -> product.getPrice()
        )).collect(Collectors.toList());

        assertEquals(actualProductsOrder, expectedProductsOrder);
    }

    @Test
    public void testFindProductsContainingQuery() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("test", "Samsung Galaxy", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 = new Product("test", "Samsung", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product3 = new Product("test", "Apple", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product1);
        productDao.save(product2);
        productDao.save(product3);

        List<Product> products = productDao.findProductsByQueryAndSortParameters("samsung Galaxy", null, null);

        assertTrue(products.contains(product1) && products.contains(product2) &&
                !products.contains(product3));
    }

    @Test
    public void testFindProductsWithNullSortFieldInRelevanceOrder() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("test", "Samsung Galaxy 45", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 = new Product("test", "Samsung", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product3 = new Product("test", "galaxy 23 samsung 1 2 4 5 6", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product1);
        productDao.save(product2);
        productDao.save(product3);

        List<Product> products = productDao.findProductsByQueryAndSortParameters("samsung Galaxy", null, null);
        int product1Index = products.indexOf(product1);
        int product2Index = products.indexOf(product2);
        int product3Index = products.indexOf(product3);

        assertTrue(product2Index < product1Index && product1Index < product3Index);
    }

    @Test
    public void testFindProductsDescriptionSortFieldAscOrder() {
        List<Product> actualProductsOrder = productDao.findProductsByQueryAndSortParameters(null,
                SortField.description, SortOrder.asc);
        List<Product> expectedProductsOrder = actualProductsOrder.stream().sorted(Comparator.comparing(
                product -> product.getDescription()
        )).collect(Collectors.toList());

        assertEquals(actualProductsOrder, expectedProductsOrder);
    }

    @Test
    public void testFindProductsDescriptionSortFieldDescOrder() {
        List<Product> actualProductsOrder = productDao.findProductsByQueryAndSortParameters(null,
                SortField.description, SortOrder.desc);
        List<Product> expectedProductsOrder = actualProductsOrder.stream().sorted(Comparator.comparing(
                product -> product.getDescription()
        )).collect(Collectors.toList());
        Collections.reverse(expectedProductsOrder);

        assertEquals(actualProductsOrder, expectedProductsOrder);
    }

    @Test
    public void testFindProductsPriceSortFieldAscOrder() {
        List<Product> actualProductsOrder = productDao.findProductsByQueryAndSortParameters(null,
                SortField.price, SortOrder.asc);
        List<Product> expectedProductsOrder = actualProductsOrder.stream().sorted(Comparator.comparing(
                product -> product.getPrice()
        )).collect(Collectors.toList());

        assertEquals(actualProductsOrder, expectedProductsOrder);
    }

    @Test
    public void testFindProductsPriceSortFieldDescOrder() {
        List<Product> actualProductsOrder = productDao.findProductsByQueryAndSortParameters(null,
                SortField.price, SortOrder.desc);
        List<Product> expectedProductsOrder = actualProductsOrder.stream().sorted(Comparator.comparing(
                product -> product.getDescription()
        )).collect(Collectors.toList());
        Collections.reverse(expectedProductsOrder);

        assertEquals(actualProductsOrder, expectedProductsOrder);
    }
}
