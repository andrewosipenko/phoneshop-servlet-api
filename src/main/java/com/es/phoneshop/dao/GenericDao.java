package com.es.phoneshop.dao;

import com.es.phoneshop.FunctionalReadWriteLock;
import com.es.phoneshop.model.IdentifiableItem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GenericDao<T extends IdentifiableItem> {
    protected final FunctionalReadWriteLock lock = new FunctionalReadWriteLock();
    protected List<T> items;
    protected Long id;

    public T get(Long id) {
        return lock.read(() -> items.stream()
                .filter(item -> item.getId().equals(id))
                .findAny()
                .orElseThrow(NoSuchElementException::new));
    }

    public void save(T item) {
        lock.write(() -> {
            Optional.ofNullable(item.getId())
                    .ifPresentOrElse(
                            (id) -> {
                                T foundItem = get(item.getId());
                                items.set(items.indexOf(foundItem), item);
                            },
                            () -> {
                                item.setId(++id);
                                items.add(item);
                            });
        });
    }

    public void delete(Long id) {
        lock.write(() -> {
            items.removeIf(item -> item.getId().equals(id));
        });
    }
}
