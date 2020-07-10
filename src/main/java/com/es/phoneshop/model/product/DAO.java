package com.es.phoneshop.model.product;

import java.util.List;

public interface DAO<Entity, KeyValue> {

    Entity get(KeyValue primaryKey);

    List<Entity> getAll();

    //why not create and update? updated entity should be returned
    void save(Entity entity);

    void delete(KeyValue keyValue);
}
