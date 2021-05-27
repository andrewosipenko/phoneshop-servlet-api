package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {
	private ProductDao productDao;

	@Before
	public void setup() {
		productDao = new ArrayListProductDao();
	}

	@Test
	public void testSaveProduct() {
		Currency usd = Currency.getInstance("USD");
		Product product = new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
		productDao.save(product);

		assertTrue(productDao.findProducts().contains(product));
	}

	@Test
	public void testSaveProductWithId() {
		Currency usd = Currency.getInstance("USD");
		Product product = new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
		productDao.save(product);

		final long id = product.getId();
		Product updProduct = new Product(id, "siemens75", "Siemens SXG75", new BigDecimal(200), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
		productDao.save(updProduct);

		assertFalse(productDao.findProducts().contains(product));
		assertTrue(productDao.findProducts().contains(updProduct));
	}

	@Test
	public void testGetProduct() {
		Currency usd = Currency.getInstance("USD");
		Product product = new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
		productDao.save(product);

		final long id = product.getId();
		assertTrue(productDao.getProduct(id).isPresent());
	}

	@Test
	public void testDeleteAndGetProductNull() {
		Currency usd = Currency.getInstance("USD");
		Product product = new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
		productDao.save(product);
		final long id = product.getId();
		assertTrue(productDao.getProduct(id).isPresent());

		productDao.delete(id);
		assertFalse(productDao.getProduct(id).isPresent());
	}

	@Test
	public void testFindProducts() {
		Currency usd = Currency.getInstance("USD");
		Product product = new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
		productDao.save(product);

		assertFalse(productDao.findProducts().isEmpty());
		assertTrue(productDao.findProducts().contains(product));
	}

	@Test
	public void testFindProductsZeroStock() {
		Currency usd = Currency.getInstance("USD");
		Product product = new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
		productDao.save(product);
		final long id = product.getId();

		assertTrue(productDao.getProduct(id).isPresent());
		assertFalse(productDao.findProducts().contains(product));
	}
}
