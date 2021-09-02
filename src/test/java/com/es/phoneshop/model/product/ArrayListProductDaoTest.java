package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

public class ArrayListProductDaoTest {
	private ProductDao productDao;
	private Product newProduct;

	@Before
	public void setup() {
		productDao = new ArrayListProductDao();
		newProduct = new Product(17L, "htces4g", "HTC UVO Short 4G", new BigDecimal(32),
				Currency.getInstance("USD"), 3, "HTC/HTC%20EVO%20Shift%204G.jpg");
	}

	@Test
	public void testFindProductsNoResults() {
		assertFalse(productDao.findProducts().isEmpty());
	}

	@Test
	public void testSavingAndGettingProduct() throws NoElementsFoundException, IdIsNotValidException, ProductIsNullException {
		productDao.save(newProduct);
		
		assertEquals(newProduct, productDao.getProduct(newProduct.getId()));
		assertNotNull(productDao.getProduct(newProduct.getId()));
	}

	@Test
	public void testGettingProductAfterDeleting() {
		
	}

	@Test
	public void testSavingDifferentProductsWithOneId() throws NoElementsFoundException, IdIsNotValidException, ProductIsNullException {
		Product oldProduct = new Product(17L, "htces4g", "HTC UVO Short 5G", new BigDecimal(42),
				Currency.getInstance("USD"), 8, "HTC/HTC%20EVO%20Shift%204G.jpg");
		productDao.save(oldProduct);
		productDao.save(newProduct);
		
		assertEquals(productDao.getProduct(17L), newProduct);
		assertNotEquals(productDao.getProduct(17L),oldProduct);
	}

	@Test
	public void testAreRequestedProductsSatisfyRequerments() {
		
		assertTrue(productDao.findProducts()
				.stream()
				.allMatch(p -> (p.getStock()>0 && p.getPrice() != null)));
	}

	
	
}
