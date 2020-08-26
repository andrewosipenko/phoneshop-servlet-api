package com.es.phoneshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GenericArrayListDao<Entity extends IEntity> implements DAO<Entity> {
    protected long maxID;

    protected List<Entity> items;

    protected final ReadWriteLock readWriteLock;
    protected final Lock readLock;
    protected final Lock writeLock;

    {
        readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    protected GenericArrayListDao() {
        this.items = new ArrayList<>();
    }

    protected GenericArrayListDao(List<Entity> items) {
        this.items = items;
    }

    @Override
    public Optional<Entity> getItem(Long id) {
        readLock.lock();
        try {
            return items.stream()
                    .filter(entity -> id.equals(entity.getId()))
                    .findAny();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Entity> getAll() {
        readLock.lock();
        try {
            return items;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void save(Entity item) {
        writeLock.lock();
        try {
            item.setId(++maxID);
            items.stream()
                    .filter(productFromList -> productFromList.getId().equals(item.getId()))
                    .findAny()
                    .ifPresentOrElse
                    //if product already exist in collection swap them
                            (productFromList -> {
                                        items.remove(productFromList);
                                        items.add(item);
                                    },
                                    //else if product isn't exist in collection simply add it
                                    () -> items.add(item));
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public void delete(Long id) {
        writeLock.lock();
        try {
            items.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findFirst()
                    .ifPresent(items::remove);
        } finally {
            writeLock.unlock();
        }

    }
}
