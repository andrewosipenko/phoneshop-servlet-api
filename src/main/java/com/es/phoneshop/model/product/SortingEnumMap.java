package com.es.phoneshop.model.product;

import java.util.Comparator;
import java.util.EnumMap;

public class SortingEnumMap {
    private final EnumMap<SortingParams, Comparator<Product>> enumMap;

    public SortingEnumMap(){
        this.enumMap = new EnumMap<>(SortingParams.class);

        enumMap.put(SortingParams.descriptionAsc, Comparator
                .comparing(Product::getDescription));

        enumMap.put(SortingParams.descriptionDesc, Comparator
                .comparing(Product::getDescription).reversed());

        enumMap.put(SortingParams.priceAsc,
                Comparator.comparing(Product::getPrice));

        enumMap.put(SortingParams.priceDesc,
                Comparator.comparing(Product::getPrice).reversed());
    }

    public Comparator<Product> get(SortingParams sortingParam){
        return enumMap.get(sortingParam);
    }
}
