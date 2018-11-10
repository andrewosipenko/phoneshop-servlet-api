package com.es.phoneshop.dao.impl;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.Product;

public class ArrayListProductDao implements ProductDao {

  private static final Predicate<Product> NON_NULL_PRICE = product -> Objects.nonNull( product.getPrice() );
  private static final Predicate<Product> STOCK_LEVEL_HIGHER_THAN_ZERO = product -> product.getStock() > 0;

  private final List<Product> products;
  private final AtomicLong currentId = new AtomicLong(1);

  public ArrayListProductDao(List<Product> products) {
    this.products = new CopyOnWriteArrayList<>();
    this.products.addAll( products );
    this.products.forEach( this::populateId );
  }

  @Override
  public Product getProduct(Long id) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public List<Product> findProducts() {
    return products.stream()
      .filter( NON_NULL_PRICE )
      .filter( STOCK_LEVEL_HIGHER_THAN_ZERO )
      .collect( Collectors.toList() );
  }

  @Override
  public void save(Product product) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public void delete(Long id) {
    throw new RuntimeException("Not implemented");
  }

  private void populateId(Product product) {
    product.setId( currentId.getAndIncrement() );
  }
}
