package com.es.phoneshop.model.product.dao;

public interface TestableSingletonProductDao<Resource> extends ProductDao {
    Resource get();
    void set(Resource resource);
    void dropToDefault();
}
