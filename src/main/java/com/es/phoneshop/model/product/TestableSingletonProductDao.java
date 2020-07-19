package com.es.phoneshop.model.product;

public interface TestableSingletonProductDao<Resource> extends ProductDao {
    Resource get();
    void set(Resource resource);
    void dropToDefault();
}
