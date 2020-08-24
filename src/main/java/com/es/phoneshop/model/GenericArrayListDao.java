package com.es.phoneshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GenericArrayListDao<Entity extends IEntity> implements DAO<Entity> {
    protected long maxID;

    protected List<Entity> products;

    protected final ReadWriteLock readWriteLock;
    protected final Lock readLock;
    protected final Lock writeLock;

    {
        readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    protected GenericArrayListDao() {
        this.products = new ArrayList<>();
    }

    protected GenericArrayListDao(List<Entity> products) {
        this.products = products;
    }

    @Override
    public Optional<Entity> getItem(Long id) {
        readLock.lock();
        try {
            return products.stream()
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
            return products;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void save(Entity item) {
        writeLock.lock();
        try {
            item.setId(++maxID);
            products.stream()
                    .filter(productFromList -> productFromList.getId().equals(item.getId()))
                    .findAny()
                    .ifPresentOrElse
                    //if product already exist in collection swap them
                            (productFromList -> {
                                        products.remove(productFromList);
                                        products.add(item);
                                    },
                                    //else if product isn't exist in collection simply add it
                                    () -> products.add(item));
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public void delete(Long id) {
        writeLock.lock();
        try {
            products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findFirst()
                    .ifPresent(products::remove);
        } finally {
            writeLock.unlock();
        }

    }
}
