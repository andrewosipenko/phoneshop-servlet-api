package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

public class ArrayListProductDaoTest {
	private ProductDao productDao;

	@Before
	public void setup() {
		productDao = new ArrayListProductDao();
	}

	@Test
	public void testFindProductsNoResults() {
		assertTrue(productDao.findProducts().isEmpty());
	}

	@Test
	public void testFindProductAfterSaving() {
		Product product = new Product();
		product.setPrice(new BigDecimal(1));
		product.setStock(1);
		productDao.save(product);
		assertEquals(1, productDao.findProducts().size());
	}

	@Test
	public void testFindProductAfterSavingWithStockLess0() {
		Product product = new Product();
		product.setPrice(new BigDecimal(1));
		product.setStock(0);
		productDao.save(product);
		assertEquals(0, productDao.findProducts().size());
	}

	@Test
	public void testFindProductAfterSavingNoResults() {
		Product product = new Product();
		productDao.save(product);
		assertEquals(0, productDao.findProducts().size());
	}

	@Test
	public void testGetProductById() {
		Product product = new Product();
		Long id = 1L;
		product.setId(id);
		productDao.save(product);
		assertEquals(id, productDao.getProduct(id).getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetProductByIdNoResult() {
		productDao.getProduct(1L);
	}

	@Test
	public void testDeleteProduct() {
		Product product = new Product();
		Long id = 1L;
		product.setId(id);
		productDao.save(product);
		productDao.delete(id);
		assertEquals(0, productDao.findProducts().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteProductNoSuchProduct() {
		productDao.delete(1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveProductsWithSameId() {
		Long id = 1L;
		Product product1 = new Product();
		Product product2 = new Product();
		product1.setId(id);
		product2.setId(id);
		productDao.save(product1);
		productDao.save(product2);
	}
}