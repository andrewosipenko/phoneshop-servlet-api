package com.es.phoneshop.model.product;

import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
	private Product newProduct;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
		newProduct = new Product(17L, "htces4g", "HTC UVO Short 4G", new BigDecimal(32),
				Currency.getInstance("USD"), 3, "HTC/HTC%20EVO%20Shift%204G.jpg");
    }

    @Test
    public void testFindProductsNoResults() {
        //assertFalse(productDao.findProducts().isEmpty());
    }
    
    @Test
    public void testSavingProduct() {
    	Long id = ArrayListProductDao.getMaxId();
    	productDao.save(newProduct);
    	
    	assertTrue(productDao.getProduct(id).getId().equals(id));
    }
    
    @Test
	public void testSavingAndGettingProduct() {
    	Long id = ArrayListProductDao.getMaxId();
		productDao.save(newProduct);
		
		assertEquals(new Product(id, newProduct), productDao.getProduct(id));
		assertNotNull(productDao.getProduct(newProduct.getId()));
	}

	@Test
	public void testSavingDifferentProductsWithOneId() {
		Long id = ArrayListProductDao.getMaxId();
		Product someOldProduct = new Product("htces4g", "HTC UVO Short 5G", new BigDecimal(42),
				Currency.getInstance("USD"), 8, "HTC/HTC%20EVO%20Shift%204G.jpg");
		Product someNewProduct = new Product(id, "htces7g", "HpC UVO Short 5G", new BigDecimal(42),
				Currency.getInstance("USD"), 8, "HTC/HTC%20EVO%20Shift%204G.jpg");
		
		productDao.save(someOldProduct);
		someOldProduct.setId(id);
		productDao.save(someNewProduct);

		assertNotNull(productDao.getProduct(id).getId());
		assertEquals(productDao.getProduct(id), someNewProduct);
		assertNotEquals(productDao.getProduct(id), someOldProduct);
	}
/*
	@Test
	public void testAreRequestedProductsSatisfyRequerments() {
		
		assertTrue(productDao.findProducts()
				.stream()
				.allMatch(p -> (p.getStock()>0 && p.getPrice() != null)));
	}
	*/
}
