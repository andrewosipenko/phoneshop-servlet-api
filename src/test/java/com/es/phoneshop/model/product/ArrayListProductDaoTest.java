package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
  private ProductDao productDao;

  @Before
  public void setup() {
    productDao = new ArrayListProductDao();
  }

  @Test
  public void testFindProducts() {
    assertFalse(productDao.findProducts().isEmpty());
  }

  @Test
  public void testFindProductsWithZeroStock() {
    Currency usd = Currency.getInstance("USD");
    Product product = new Product("test-code", "test-description", new BigDecimal(100), usd, 0, "test-url");
    productDao.save(product);
    assertFalse(productDao.findProducts().contains(product));
  }

  @Test
  public void testFindProductsWithNullPrice() {
    Currency usd = Currency.getInstance("USD");
    Product product = new Product("test-code", "test-description", null, usd, 100, "test-url");
    productDao.save(product);
    assertFalse(productDao.findProducts().contains(product));
  }

  @Test
  public void testGetProduct() throws ProductNotFoundException {
    assertNotNull(productDao.getProduct(1L));
  }

  @Test (expected = ProductNotFoundException.class)
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

  @Test (expected = ProductNotFoundException.class)
  public void testDeleteProduct() throws ProductNotFoundException {
    productDao.getProduct(1L);
    productDao.delete(1L);
    productDao.getProduct(1L);
  }
}
