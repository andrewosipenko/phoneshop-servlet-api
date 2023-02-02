package com.es.phoneshop.dao;

import com.es.phoneshop.model.DaoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public abstract class GenericDao<T extends DaoEntity<T>> {
    protected List<T> items = new ArrayList<>();
    protected Long maxId = 1L;

    public synchronized T findById(Long id) {
        if (id == null) {
            throw getItemNotFoundExceptionSupplier(null).get();
        }

        return items.stream()
                .filter(item -> id.equals(item.getId()))
                .findAny()
                .orElseThrow(getItemNotFoundExceptionSupplier(id));
    }

    public synchronized List<T> findAll() {
        return items;
    }

    protected abstract Supplier<? extends NoSuchElementException> getItemNotFoundExceptionSupplier(Long id);

    public synchronized void save(T item) {
        try {
            T itemToUpdate = findById(item.getId());
            items.remove(itemToUpdate);
        } catch (NoSuchElementException e) {
            item.setId(maxId++);
        } finally {
            items.add(item);
        }
    }

    public synchronized void delete(Long id) {
        T item = findById(id);
        items.remove(item);
    }
}
