package com.es.phoneshop.domain.product.model;

import com.es.phoneshop.domain.common.model.SortingOrder;

public class ProductRequest {
    private String query;
    private ProductSortingCriteria sortingCriteria;
    private SortingOrder sortingOrder;
    private int minStockInclusive;

    public ProductRequest(String query, ProductSortingCriteria sortingCriteria, SortingOrder sortingOrder, int minStockInclusive) {
        this.query = query;
        this.sortingCriteria = sortingCriteria;
        this.sortingOrder = sortingOrder;
        this.minStockInclusive = minStockInclusive;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public SortingOrder getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(SortingOrder sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public ProductSortingCriteria getSortingCriteria() {
        return sortingCriteria;
    }

    public void setSortingCriteria(ProductSortingCriteria sortingCriteria) {
        this.sortingCriteria = sortingCriteria;
    }

    public int getMinStockInclusive() {
        return minStockInclusive;
    }

    public void setMinStockInclusive(int minStockInclusive) {
        this.minStockInclusive = minStockInclusive;
    }
}
