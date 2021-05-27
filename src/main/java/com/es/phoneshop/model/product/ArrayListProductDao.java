package com.es.phoneshop.model.product;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
	private List<Product> products;
	private long maxId;

	public ArrayListProductDao() {
		this.products = new ArrayList<>();
		this.saveSampleProducts();
	}

	@Override
	public synchronized Optional<Product> getProduct(final Long id) {
		return products.stream()
				.filter(product -> product.getId().equals(id))
				.findAny();
	}

	@Override
	public synchronized List<Product> findProducts() {
		return List.copyOf(products.stream()
				.filter(this::nonNullPrice)
				.filter(this::productIsInStock)
				.collect(Collectors.toList()));
	}

	private boolean productIsInStock(@NonNull final Product product) {
		return product.getStock() > 0;
	}

	private boolean nonNullPrice(@NonNull final Product product) {
		return product.getPrice() != null;
	}

	@Override
	public synchronized void save(@NonNull final Product product) {
		if (product.getId() != null) {
			Optional<Product> optProduct = getProduct(product.getId());
			optProduct.ifPresent(products::remove);
		} else {
			product.setId(maxId++);
		}
		products.add(product);
	}

	@Override
	public synchronized void delete(final Long id) {
		Optional<Product> optProduct = products.stream()
				.filter(product -> product.getId().equals(id))
				.findAny();
		optProduct.ifPresent(products::remove);
	}

	private void saveSampleProducts() {
		Currency usd = Currency.getInstance("USD");
		save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
		save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
		save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
		save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
		save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
		save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
		save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
		save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
		save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
		save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
		save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
		save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
		save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
	}

}


