package com.es.phoneshop.core.dao.product;

public enum SortBy {
    DESCRIPTION(1),
    PRICE(2);

    private final int sortId;

    SortBy(int sortId) {
        this.sortId = sortId;
    }

    public int getId() {
        return sortId;
    }
}
