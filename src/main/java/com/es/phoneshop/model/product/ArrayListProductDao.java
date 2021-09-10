package com.es.phoneshop.model.product;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
  private static volatile ArrayListProductDao instance;
  private ReadWriteLock lock;
  private List<Product> products;
  private long maxId;

  private ArrayListProductDao() {
    this.maxId = 1;
    this.products = new ArrayList<>();
    this.lock = new ReentrantReadWriteLock();
  }

  public static ArrayListProductDao getInstance() {
    ArrayListProductDao localInstance = instance;
    if (localInstance == null) {
      synchronized (ArrayListProductDao.class) {
        localInstance = instance;
        if (localInstance == null) {
          localInstance = new ArrayListProductDao();
          instance = localInstance;
        }
      }
    }
    return localInstance;
  }

  @Override
  public Product getProduct(Long id) throws ProductNotFoundException {
    Product result;
    lock.readLock().lock();
    try {
      result = products.stream()
              .filter(product -> product.getId().equals(id))
              .findAny()
              .orElseThrow(() -> new ProductNotFoundException(String.valueOf(id)));
    } finally {
      lock.readLock().unlock();
    }
    return result;
  }

  @Override
  public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
    List<Product> productList;
    lock.readLock().lock();
    try {
      String[] queryWords = (query == null || query.trim().isEmpty())
              ? null
              : query.trim().toLowerCase(Locale.ROOT).split("\\s");
      Comparator<Product> productComparator = getSortOrderComparator(getSortFieldComparator(queryWords, sortField),
              sortOrder);
      productList = products.stream()
              .filter(p -> p.getPrice() != null)
              .filter(p -> p.getStock() > 0)
              .filter(product -> queryWords == null
                      || Arrays.stream(queryWords).anyMatch(
                              s -> product.getDescription().toLowerCase(Locale.ROOT).contains(s)))
              .sorted(productComparator)
              .collect(Collectors.toList());
    } finally {
      lock.readLock().unlock();
    }
    return productList;
  }

  private Comparator<Product> getSortFieldComparator(String[] query, SortField sortField) {
    Comparator<Product> comparator;
    if (sortField == null) {
      comparator = Comparator.comparingLong(o -> getCountOfMatches(query, o));
    } else if (SortField.description == sortField) {
      comparator = Comparator.comparing(Product::getDescription);
    } else {
      comparator = Comparator.comparing(Product::getPrice);
    }
    return comparator;
  }

  private Comparator<Product> getSortOrderComparator(Comparator<Product> comparator, SortOrder sortOrder) {
    if (sortOrder == null || SortOrder.desc == sortOrder) {
      comparator = comparator.reversed();
    }
    return comparator;
  }

  private long getCountOfMatches(String[] query, Product product) {
    long count = 0;
    if (query != null) {
      count = Arrays.stream(query)
              .filter(s -> product.getDescription().toLowerCase(Locale.ROOT).contains(s))
              .count();
    }
    return count;
  }

  @Override
  public void save(Product product) {
    Long productId = product.getId();
    lock.writeLock().lock();
    try {
      if (productId == null) {
        product.setId(maxId++);
        products.add(product);
      } else {
        Product productToUpdate = getProduct(productId);
        populateExistingProduct(product, productToUpdate);
      }
    } catch (ProductNotFoundException e) {
      products.add(product);
    } finally {
      lock.writeLock().unlock();
    }
  }

  @Override
  public void delete(Long id) throws ProductNotFoundException {
    lock.writeLock().lock();
    try {
      products.remove(getProduct(id));
    } finally {
      lock.writeLock().unlock();
    }
  }

  private void populateExistingProduct(Product product, Product productToUpdate) {
    productToUpdate.setCode(product.getCode());
    productToUpdate.setCurrency(product.getCurrency());
    productToUpdate.setPrice(product.getPrice());
    productToUpdate.setStock(product.getStock());
    productToUpdate.setDescription(product.getDescription());
    productToUpdate.setImageUrl(product.getImageUrl());
  }
}
