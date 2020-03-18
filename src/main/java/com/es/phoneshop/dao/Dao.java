package com.es.phoneshop.dao;

public interface Dao<T> {
    void save(T item);

    T get(Long id);

    void delete(Long id);
}
