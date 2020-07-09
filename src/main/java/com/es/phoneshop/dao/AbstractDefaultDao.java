package com.es.phoneshop.dao;

import com.es.phoneshop.model.DaoItem;

import java.util.List;
import java.util.NoSuchElementException;

public class AbstractDefaultDao<T extends DaoItem> implements DefaultDao<T> {

    private List<T> items;

    public void init(List<T> items) {
        this.items = items;
    }

    @Override
    public List<T> getAll() {
        return items;
    }

    @Override
    public T getById(Long id) {
        if (id == null) {
            new IllegalArgumentException();
        }

        return items.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void save(T object) {
        if (object == null) {
            new IllegalArgumentException();
        }

        if (items.stream().noneMatch(t -> t.getId().equals(object.getId()))) {
            items.add(object);
        } else {
            new IllegalArgumentException();
        }
    }


    //todo think about realization
    @Override
    public void saveAll(List<T> objects) {
        objects.stream().forEach(this::save);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            new IllegalArgumentException();
        }

        if (!items.stream().noneMatch(t -> t.getId().equals(id))) {
            items.remove(id);
        } else {
            new IllegalArgumentException();
        }
    }
}
