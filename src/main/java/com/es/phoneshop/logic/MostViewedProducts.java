package com.es.phoneshop.logic;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class MostViewedProducts {
    private List<Product> mostViewedProducts;
    ProductDao productDao = ArrayListProductDao.getInstance();

    public List<Product> getMostViewedProducts(){
        return mostViewedProducts;
    }

    public MostViewedProducts(){
        this.mostViewedProducts= new ArrayList<>();
    }
    public void update(){
        List<Product> products = productDao.getList();

        Product first = products.get(0), second = products.get(0), third = products.get(0);
        for(int i = 0; i < products.size(); ++i){
            if(products.get(i).getAmmountOfViews() > third.getAmmountOfViews()){
                third = products.get(i);
                if(third.getAmmountOfViews() > second.getAmmountOfViews()){
                    Product product = third;
                    third = second;
                    second = product;
                    if(second.getAmmountOfViews() > first.getAmmountOfViews()){
                        Product term = second;
                        second = first;
                        first = term;
                    }
                }
            }
        }
        this.mostViewedProducts.clear();
        mostViewedProducts.add(first);
        if(!mostViewedProducts.contains(second) && second.getAmmountOfViews() != 0)
        mostViewedProducts.add(second);
        if(!mostViewedProducts.contains(third) && third.getAmmountOfViews() != 0)
        mostViewedProducts.add(third);
    }
}
