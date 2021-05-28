package com.es.phoneshop.domain.product.model;

public enum ProductSortingCriteria {
    PRICE, DESCRIPTION;

    public static ProductSortingCriteria fromString(String str){
        for (ProductSortingCriteria value :
                ProductSortingCriteria.values()) {
            if (value.toString().equalsIgnoreCase(str)) {
                return value;
            }
        }
        return null;
    }
}
