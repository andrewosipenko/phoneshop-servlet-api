package com.es.phoneshop.model.product;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
  private static ProductDao productDao;
  private static Currency usd;

  @BeforeClass
  public static void setup() {
    productDao = ArrayListProductDao.getInstance();
    usd = Currency.getInstance("USD");
    setTestData();
  }

  private static void setTestData() {
    productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
    productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
    productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(100), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
  }

  @Test
  public void testFindProducts() {
    Currency usd = Currency.getInstance("USD");
    List<Product> testList = new ArrayList<>();
    testList.add(new Product(1L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
    testList.add(new Product(2L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
    testList.removeAll(productDao.findProducts("Samsung", null, null));
    assertTrue(testList.isEmpty());
  }

  @Test
  public void testFindProductsNullQuery() {
    assertFalse(productDao.findProducts(null, null, null).isEmpty());
  }

  @Test
  public void testFindProductsWithZeroStock() {
    Currency usd = Currency.getInstance("USD");
    Product product = new Product("test-code", "test-description", new BigDecimal(100), usd, 0, "test-url");
    productDao.save(product);
    assertFalse(productDao.findProducts(null, null, null).contains(product));
  }

  @Test
  public void testFindProductsWithNullPrice() {
    Currency usd = Currency.getInstance("USD");
    Product product = new Product("test-code", "test-description", null, usd, 100, "test-url");
    productDao.save(product);
    assertFalse(productDao.findProducts(null, null, null).contains(product));
  }

  @Test
  public void testSortProductsDescending() {
    Currency usd = Currency.getInstance("USD");
    List<Product> testList = new ArrayList<>();
    testList.add(new Product(3L, "iphone", "Apple iPhone", new BigDecimal(100), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
    testList.add(new Product(1L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
    testList.add(new Product(2L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
    assertEquals(testList, productDao.findProducts("h A", null, null));
  }

  @Test
  public void testSortProductsAscending() {
    Currency usd = Currency.getInstance("USD");
    List<Product> testList = new ArrayList<>();
    testList.add(new Product(1L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
    testList.add(new Product(2L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
    testList.add(new Product(3L, "iphone", "Apple iPhone", new BigDecimal(100), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
    assertEquals(testList, productDao.findProducts("h A", null, SortOrder.asc));
  }

  @Test
  public void testSortProductsByDescriptionDescending() {
    Currency usd = Currency.getInstance("USD");
    List<Product> testList = new ArrayList<>();
    testList.add(new Product(2L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
    testList.add(new Product(1L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
    testList.add(new Product(3L, "iphone", "Apple iPhone", new BigDecimal(100), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
    assertEquals(testList, productDao.findProducts("", SortField.description, null));
  }

  @Test
  public void testSortProductsByPriceDescending() {
    Currency usd = Currency.getInstance("USD");
    List<Product> testList = new ArrayList<>();
    testList.add(new Product(2L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
    testList.add(new Product(1L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 2, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
    testList.add(new Product(3L, "iphone", "Apple iPhone", new BigDecimal(100), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
    assertEquals(testList, productDao.findProducts("", SortField.description, null));
  }

  @Test
  public void testGetProduct() throws ProductNotFoundException {
    assertNotNull(productDao.getProduct(1L));
  }

  @Test(expected = ProductNotFoundException.class)
  public void testGetProductNotFound() throws ProductNotFoundException {
    assertNotNull(productDao.getProduct(null));
  }

  @Test
  public void testSaveNewProduct() throws ProductNotFoundException {
    Currency usd = Currency.getInstance("USD");
    Product product = new Product("test-code", "test-description", new BigDecimal(100), usd, 100, "test-url");

    productDao.save(product);
    assertTrue(product.getId() >= 0);
    Product actual = productDao.getProduct(product.getId());
    assertEquals("test-code", actual.getCode());
  }

  @Test
  public void testSaveUpdateProduct() throws ProductNotFoundException {
    Currency usd = Currency.getInstance("USD");
    Product product = new Product("test-code", "test-description", new BigDecimal(100), usd, 100, "test-url");
    productDao.save(product);
    Product productUpdate = new Product(product.getId(), "updated-code", "updated-description", new BigDecimal(10), usd, 10, "updated-url");
    productDao.save(productUpdate);
    Product actual = productDao.getProduct(product.getId());
    assertEquals("updated-code", actual.getCode());
  }

  @Test(expected = ProductNotFoundException.class)
  public void testDeleteProduct() throws ProductNotFoundException {
    productDao.getProduct(1L);
    productDao.delete(1L);
    productDao.getProduct(1L);
  }
}
