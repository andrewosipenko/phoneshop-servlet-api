package com.es.phoneshop.model.recentlyViewed;

import com.es.phoneshop.model.product.entity.Product;

import java.util.List;

public interface RecentlyViewedService<SessionResource> {

    List<Product> getList(SessionResource sessionResource);

    void add(List<Product> products, Product product);
}
