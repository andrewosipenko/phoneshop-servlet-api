package com.es.phoneshop.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import com.es.phoneshop.model.comparator.SortingComparator;
import com.es.phoneshop.model.filter.Filter;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

public class ArrayListProductDao implements ProductDao {

	private static volatile ArrayListProductDao instance;
	private List<Product> products;
	public final ReadWriteLock readWriteLock;
	private static Long maxId;

	private ArrayListProductDao() {
		products = new ArrayList<Product>();
		this.readWriteLock = new ReentrantReadWriteLock();
		if(maxId == null) {
			maxId = (long) products.size() + 1;
		}
	}
	
	public static ArrayListProductDao getInstance() {
		
		ArrayListProductDao result = instance;
		if(result != null) {
			return result;
		}
		
		synchronized(ArrayListProductDao.class) {
			if(instance == null) {
				instance = new ArrayListProductDao();
			}
			return instance;
		}
		
	}

    @Override
    public Product getProduct(Long id) {

    	Product buf;
    	
		if (id == null) {
			throw new IllegalArgumentException("Id is null");
			}

		readWriteLock.readLock().lock();
		buf = products.stream()
				.filter(p -> id.equals(p.getId()))
				.findAny()
				.orElseThrow(ProductNotFoundException::new);
		readWriteLock.readLock().unlock();
		
		return buf;
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
    			return SortingComparator.sortProducts(p1, p2, filter);
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
    	products.removeIf(p -> id.equals(p.getId()));
    	readWriteLock.writeLock().unlock();

    }

    public static Long getMaxId() {
    	return ArrayListProductDao.maxId;
    }

}
