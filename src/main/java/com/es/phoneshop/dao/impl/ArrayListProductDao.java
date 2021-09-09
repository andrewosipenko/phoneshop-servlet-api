package com.es.phoneshop.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import com.es.phoneshop.model.filter.Filter;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

public class ArrayListProductDao implements ProductDao {
	
	private List<Product> products;
	public final ReadWriteLock readWriteLock;
	private static Long maxId;
	
	public ArrayListProductDao() {
		products = getSampleProducts();
		this.readWriteLock = new ReentrantReadWriteLock();
		if(maxId == null) {
			maxId = (long) products.size() + 1;
		}
	}
	
    @Override
    public Product getProduct(Long id) {
    	
		if (id == null) {
			throw new IllegalArgumentException("Id is null");
			}
		
		readWriteLock.readLock().lock();
		try {
			return products.stream()
					.filter(p -> id.equals(p.getId()))
					.findAny()
					.get();
		} catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("No products with current id were found");
		} finally {
			readWriteLock.readLock().unlock();
		}
		
    }

    @Override
    public List<Product> findProducts(Filter filter) {
       
    	readWriteLock.readLock().lock();
    	
    	List<Product> prods = products.stream()
    			.filter(p -> {
    				if(filter.getQueryWords().size() == 0) {
    					return true;
    				} else {
    					return filter.percentOfWords(p) > 0 ?
    		    				true : false;
    				}})
    			.sorted((p1, p2) -> {
    			return (int)(10*(filter.percentOfWords(p2)-filter.percentOfWords(p1)));
    			})
    			.collect(Collectors.toList());
    	
	/*	List<Product> prods = products.stream()
				.filter(p -> p.getStock() > 0 && p.getPrice() != null)
				.collect(Collectors.toList());*/
		readWriteLock.readLock().unlock();
		
		return prods;
    }

    @Override
    public void save(Product product) {
        
    	if(product == null) {
    		throw new IllegalArgumentException("Product is null");
    	}
	
    	List<Product> sameIdProducts;

    	readWriteLock.writeLock().lock();
    	if(product.getId() == null) {
    		products.add(new Product(maxId++, product));
    	} else {
    		sameIdProducts = products.stream()
        			.filter(p -> product.getId().equals(p.getId()))
        			.collect(Collectors.toList());
    		
        	if(sameIdProducts.size()<1) {
        		throw new ProductNotFoundException("No products with current id were found");
        	} else {
        		products.set(products.indexOf(sameIdProducts.get(0)), product);
    		}
    	}
    	readWriteLock.writeLock().unlock();
    	
    }

    @Override
    public void delete(Long id) {
        
    	if(id == null) {
			throw new IllegalArgumentException("Id is null");
		}
		
    	readWriteLock.writeLock().lock();
		try {
			products.remove(products.stream()
					.filter(p -> id.equals(p.getId()))
					.findFirst()
					.get());
		}catch(NoSuchElementException ex) {
			throw new ProductNotFoundException("No products with current id were found");
		} finally {
			readWriteLock.writeLock().unlock();
		}
		
    }

    private List<Product> getSampleProducts(){
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        result.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "/Samsung/Samsung%20Galaxy%20S.jpg"));
        result.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        result.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        result.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "/Apple/Apple%20iPhone.jpg"));
        result.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "/Apple/Apple%20iPhone%206.jpg"));
        result.add(new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "/HTC/HTC%20EVO%20Shift%204G.jpg"));
        result.add(new Product(7L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "/Sony/Sony%20Ericsson%20C901.jpg"));
        result.add(new Product(8L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "/Sony/Sony%20Xperia%20XZ.jpg"));
        result.add(new Product(9L, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "/Nokia/Nokia%203310.jpg"));
        result.add(new Product(10L, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "/Palm/Palm%20Pixi.jpg"));
        result.add(new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "/Siemens/Siemens%20C56.jpg"));
        result.add(new Product(12L, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "/Siemens/Siemens%20C61.jpg"));
        result.add(new Product(13L, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "/Siemens/Siemens%20SXG75.jpg"));

        return result;
    }
    
    public static Long getMaxId() {
    	return ArrayListProductDao.maxId;
    }

}
