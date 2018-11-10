package com.es.phoneshop.dao;

import java.util.List;

import com.es.phoneshop.model.Product;

public interface ProductDao {
  Product getProduct( Long id );

  List<Product> findProducts();

  void save( Product product );

  void delete( Long id );
}
