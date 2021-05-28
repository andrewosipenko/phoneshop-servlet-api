package com.es.phoneshop.domain.product.model;

import com.es.phoneshop.domain.common.model.SortingOrder;

import java.util.Currency;

public class ProductRequest {
    private String query;
    private SortingOrder descriptionSort;
    private SortingOrder priceSort;

    public ProductRequest(String query, SortingOrder descriptionSort, SortingOrder priceSort) {
        this.query = query;
        this.descriptionSort = descriptionSort;
        this.priceSort = priceSort;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public SortingOrder getDescriptionSort() {
        return descriptionSort;
    }

    public void setDescriptionSort(SortingOrder descriptionSort) {
        this.descriptionSort = descriptionSort;
    }

    public SortingOrder getPriceSort() {
        return priceSort;
    }

    public void setPriceSort(SortingOrder priceSort) {
        this.priceSort = priceSort;
    }
}
