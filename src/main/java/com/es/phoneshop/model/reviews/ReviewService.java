package com.es.phoneshop.model.reviews;

import com.es.phoneshop.model.product.Product;

public interface ReviewService {

    void add(Product product, Review review);
}
