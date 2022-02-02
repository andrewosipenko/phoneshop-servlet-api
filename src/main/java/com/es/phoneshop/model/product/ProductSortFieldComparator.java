package com.es.phoneshop.model.product;

import java.util.Comparator;

public class ProductSortFieldComparator implements Comparator<Product> {
    final SortType sortType;
    final SortField sortField;

    public ProductSortFieldComparator(SortType sortType, SortField sortField) {
        this.sortType = sortType;
        this.sortField = sortField;
    }

    @Override
    public int compare(Product a, Product b) {
            int result = 0;
            if (sortField == null) {
                return 0;
            } else if (sortField == SortField.description) {
                result = a.getDescription().compareTo(b.getDescription());
            } else if (sortField == SortField.price) {
                result = a.getPrice().compareTo(b.getPrice());
            }
            if (sortType == SortType.desc) {
                result *= -1;
            }
            return result;
    }
}
