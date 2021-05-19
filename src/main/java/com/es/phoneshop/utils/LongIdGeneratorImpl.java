package com.es.phoneshop.utils;

public class LongIdGeneratorImpl implements LongIdGenerator {

    private Long nextAvailableId;

    public LongIdGeneratorImpl() {
        this.nextAvailableId = 500L;
    }

    public LongIdGeneratorImpl(Long initialValue){
        this.nextAvailableId = initialValue;
    }

    @Override
    public Long getId() {
        return nextAvailableId++;
    }
}
