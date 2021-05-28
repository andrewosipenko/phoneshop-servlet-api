package com.es.phoneshop.domain.common.model;

public enum SortingOrder {
    ASC, DESC;

    public static SortingOrder fromString(String str){
        for (SortingOrder value :
                SortingOrder.values()) {
            if (value.toString().equalsIgnoreCase(str)) {
                return value;
            }
        }
        return null;
    }
}
