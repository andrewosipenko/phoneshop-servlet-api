package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

	private List<Product> products;
	private final Object lock = new Object();

	public ArrayListProductDao() {
		products = getSampleProducts();
	}
	
	@Override
	public Product getProduct(Long id) throws NoElementsFoundException, IdIsNotValidException {

		synchronized(lock){
			if (id == null) {
				throw new IdIsNotValidException("Id is null");
			}

			try {
				return products.stream()
						.filter(p -> id.equals(p.getId()))
						.findAny()
						.get();
			} catch (NoSuchElementException ex) {
				throw new NoElementsFoundException("No elements with current id were found");
			}
		}
	}

	@Override
	public List<Product> findProducts() {
		
		synchronized(lock) {
			return products.stream()
					.filter(p -> p.getStock() > 0)
					.filter(p -> p.getPrice() != null)
					.collect(Collectors.toList());
		}
	}

	@Override
	public void save(Product product) throws ProductIsNullException {
		
		if(product == null) {
			throw new ProductIsNullException("Product is null");
		}
		
		List<Product> sameIdProducts;
		
		synchronized(lock) {
			sameIdProducts = products.stream()
					.filter(p -> product.getId().equals(p.getId()))
					.collect(Collectors.toList());
			
			if(sameIdProducts.size()<1) {
				products.add(product);
			} else {
				products.set(products.indexOf(sameIdProducts.get(0)), product);
				
			}
		}
	}

	@Override
	public void delete(Long id) throws IdIsNotValidException {
		
		if(id == null) {
			throw new IdIsNotValidException("Id is null");
		}
		
		synchronized(lock) {
			try {
				products.remove(products.stream()
						.filter(p -> id.equals(p.getId()))
						.findFirst()
						.get());
			}catch(NoSuchElementException ex) {
				throw new IdIsNotValidException("No elements with current id were found");
			}
		}
	}

	private List<Product> getSampleProducts() {
		
		List<Product> result = new ArrayList<Product>();
		Currency usd = Currency.getInstance("USD");
		
		result.add(new Product(1L, "sgs", "Samsung Galaxy S", 			new BigDecimal(100), usd, 100, "Samsung/Samsung%20Galaxy%20S.jpg"));
		result.add(new Product(2L, "sgs2", "Samsung Galaxy S II", 		new BigDecimal(200), usd, 0, "Samsung/Samsung%20Galaxy%20S%20II.jpg"));
		result.add(new Product(3L, "sgs3", "Samsung Galaxy S III", 		new BigDecimal(300), usd, 5, "Samsung/Samsung%20Galaxy%20S%20III.jpg"));
		result.add(new Product(4L, "iphone", "Apple iPhone",			new BigDecimal(200), usd, 10, "Apple/Apple%20iPhone.jpg"));
		result.add(new Product(5L, "iphone6", "Apple iPhone 6", 		new BigDecimal(1000), usd, 30, "Apple/Apple%20iPhone%206.jpg"));
		result.add(new Product(6L, "htces4g", "HTC EVO Shift 4G", 		new BigDecimal(320), usd, 3, "HTC/HTC%20EVO%20Shift%204G.jpg"));
		result.add(new Product(7L, "sec901", "Sony Ericsson C901", 		new BigDecimal(420), usd, 30, "Sony/Sony%20Ericsson%20C901.jpg"));
		result.add(new Product(8L, "xperiaxz", "Sony Xperia XZ",		new BigDecimal(120), usd, 100, "Sony/Sony%20Xperia%20XZ.jpg"));
		result.add(new Product(9L, "nokia3310", "Nokia 3310", 			new BigDecimal(70), usd, 100, "Nokia/Nokia%203310.jpg"));
		result.add(new Product(10L, "palmp", "Palm Pixi", 				new BigDecimal(170), usd, 30, "Palm/Palm%20Pixi.jpg"));
		result.add(new Product(11L, "simc56", "Siemens C56", 			new BigDecimal(70), usd, 20, "Siemens/Siemens%20C56.jpg"));
		result.add(new Product(12L, "simc61", "Siemens C61", 			new BigDecimal(80), usd, 30, "Siemens/Siemens%20C61.jpg"));
		result.add(new Product(13L, "simsxg75", "Siemens SXG75", 		new BigDecimal(150), usd, 40, "Siemens/Siemens%20SXG75.jpg"));

		return result;
	}
}
