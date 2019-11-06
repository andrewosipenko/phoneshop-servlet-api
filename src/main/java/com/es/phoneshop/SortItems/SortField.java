package com.es.phoneshop.SortItems;

public enum SortField {
    price, description;
    public static SortField lookup(String name){
        SortField sortField;
        try{
            sortField = SortField.valueOf(name);
        }catch (Exception e){
            sortField = null;
        }
        return sortField;
    }
}
