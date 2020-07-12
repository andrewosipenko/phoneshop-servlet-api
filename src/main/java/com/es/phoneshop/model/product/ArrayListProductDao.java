package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListProductDao implements ProductDao {
    private final List<Product> products;

    private final ReadWriteLock readWriteLock;
    private final Lock readlock;
    private final Lock writelock;

    {
        readWriteLock = new ReentrantReadWriteLock();
        readlock = readWriteLock.readLock();
        writelock = readWriteLock.writeLock();
    }

    public ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    public ArrayListProductDao(List<Product> products){
        this.products = products;
    }

    //is it better then return optional?
    @Override
    public Product get(Long id) throws NoSuchElementException {
        readlock.lock();
        try{
            return products.stream().
                    filter(product -> id.equals(product.getId())).
                    findAny().
                    get();
        }
        finally {
            readlock.unlock();
        }
    }

    @Override
    public List<Product> getAll() {
        readlock.lock();
        try{
            return products;
        }
        finally {
            readlock.unlock();
        }
    }

    //in my humble opinion it is wrong implementation of create/update
    //semantically it's probably wrong usage of orElseGet, but pretty compact
    @Override
    public void save(Product product) {
        writelock.lock();
        try {
            products.stream().
                    filter(productFromList -> productFromList.getId().equals(product.getId())).
                    findAny().
                    ifPresentOrElse(
                            //if product already exist in collection swap them
                            productFromList->{
                                products.remove(productFromList);
                                products.add(product);
                            },
                            //else if product isn't exist in collection simply add it 
                            ()-> products.add(product));
        }
        finally {
            writelock.unlock();
        }

    }

    //imho lazy foreach could be better
    @Override
    public void delete(Long id) {
        writelock.lock();
        try {
            products.stream().
                    filter(product -> id.equals(product.getId())).
                    findFirst().
                    ifPresent(products::remove);
        }
        finally {
            writelock.unlock();
        }

    }


}
