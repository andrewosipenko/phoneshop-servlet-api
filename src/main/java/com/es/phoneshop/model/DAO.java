package com.es.phoneshop.model;

import java.util.List;
import java.util.Optional;

public interface DAO<Entity> {

    Optional<Entity> getItem(Long id);

    List<Entity> getAll();

    void save(Entity entity);

    void delete(Long id);
}
