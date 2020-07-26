package com.es.phoneshop.model.product.dao;

import com.es.phoneshop.model.product.dao.ProductDao;

public interface TestableSingletonProductDao<Resource> extends ProductDao {
    Resource get();
    void set(Resource resource);
    void dropToDefault();
}
