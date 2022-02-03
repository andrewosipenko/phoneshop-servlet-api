package com.es.phoneshop.model.product;

public enum SortingParams {
    descriptionAsc, descriptionDesc, priceAsc, priceDesc, defaultSort;

    public static boolean isExist(String line){
        if(line == null){
            return false;
        }
        for(SortingParams sortParam : SortingParams.values()){
            if(line.equals(String.valueOf(sortParam))){
                return true;
            }
        }
        return false;
    }
}
