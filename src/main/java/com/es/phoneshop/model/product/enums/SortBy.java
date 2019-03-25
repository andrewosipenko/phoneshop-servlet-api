package com.es.phoneshop.model.product.enums;

public enum SortBy {
    DESCRIPTION(1),
    PRICE(2);

    private int sortId;

    SortBy(int sortId) {
        this.sortId = sortId;
    }

    public int getId() {
        return sortId;
    }
}
