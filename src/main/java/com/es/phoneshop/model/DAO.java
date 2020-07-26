package com.es.phoneshop.model;

import java.util.List;
import java.util.Optional;

public interface DAO<Entity, KeyValue> {

    Optional<Entity> get(KeyValue primaryKey);

    List<Entity> getAll();

    //why not create and update? updated entity should be returned
    void save(Entity entity);

    void delete(KeyValue keyValue);
}
