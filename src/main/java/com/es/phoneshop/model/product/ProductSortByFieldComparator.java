package com.es.phoneshop.model.product;
import java.util.Comparator;

public class ProductSortByFieldComparator implements Comparator<Product> {
    SortField sortField;

    ProductSortByFieldComparator(SortField sortField){
        this.sortField = sortField;
    }
    @Override
    public int compare(Product left, Product right) {
        if(sortField == null){
            return 0;
        }else{
            if(SortField.description == sortField){
                return left.getDescription().compareToIgnoreCase(right.getDescription());
            }else{
                return left.getPrice().compareTo(right.getPrice());
            }
        }
    }
}
