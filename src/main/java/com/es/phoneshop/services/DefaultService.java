package com.es.phoneshop.services;

public interface DefaultService<T> {
    T getById(Long id);

    void save(T item);

    T update(T newItem);

    void delete(T delete);
}
