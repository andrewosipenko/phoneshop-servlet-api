package com.es.phoneshop.utils;

import java.util.concurrent.atomic.AtomicLong;

public class LongIdGeneratorImpl implements LongIdGenerator {

    private final AtomicLong nextAvailableId;

    public LongIdGeneratorImpl() {
        this.nextAvailableId = new AtomicLong(500L);
    }

    public LongIdGeneratorImpl(Long initialValue){
        this.nextAvailableId = new AtomicLong(initialValue);
    }

    @Override
    public Long getId() {
        return nextAvailableId.getAndIncrement();
    }
}
