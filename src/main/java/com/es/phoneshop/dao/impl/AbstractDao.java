package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.Dao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbstractDao<T extends Item> implements Dao<T> {
    List<T> items = new ArrayList<>();

    @Override
    public synchronized void save(T item) {
        Optional<T> saveItem = items.stream()
                .filter(item1 -> item1.getId().equals(item.getId()))
                .findAny();
        if (saveItem.isPresent()) {
            throw new IllegalArgumentException("Object's id isn't unic");
        } else {
            items.add(item);
        }
    }

    @Override
    public T get(Long id) {
        return items.stream()
                .filter(product -> product.getId().equals(id)).findAny()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        T delItem = this.get(id);
        items.remove(delItem);
    }
}
