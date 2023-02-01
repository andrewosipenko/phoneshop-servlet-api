package com.es.phoneshop.model.product.dao;

import com.es.phoneshop.model.product.entity.Entity;
import com.es.phoneshop.model.product.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class EntityDao<T extends Entity> {
    protected List<T> entities;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public T getEntity(Long id, String message) throws EntityNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Parameter id is null");
        }
        lock.readLock().lock();
        try {
            T entity = entities.stream().
                    filter(currEntity -> id.equals(currEntity.getId())).
                    findAny().
                    orElseThrow(() -> new EntityNotFoundException(String.format("%s:%d", message, id)));
            return entity;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void save(T entity) {
        lock.writeLock().lock();
        try {
            if (entity.getId() != null) {
                Optional<T> entityToAdd = entities.stream().
                        filter(currEntity -> currEntity.getId().equals(entity.getId())).
                        findAny();
                if (entityToAdd.isEmpty()) {
                    addNewEntity(entity, entities);
                } else {
                    entities.set(entities.indexOf(entityToAdd.get()), entity);
                }
            } else {
                addNewEntity(entity, entities);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    protected abstract void addNewEntity(T entity, List<T> entities);
}
