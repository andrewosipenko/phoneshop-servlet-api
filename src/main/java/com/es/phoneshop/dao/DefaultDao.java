package com.es.phoneshop.dao;

import java.util.List;

public interface DefaultDao<T> {
    List<T> getAll();

    T getById(Long id);

    void save(T object);

    void saveAll(List<T> objects);

    void deleteById(Long id);
}
