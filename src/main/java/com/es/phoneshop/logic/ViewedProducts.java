package com.es.phoneshop.logic;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.CartService.*;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ViewedProducts {
    private final Queue<Product> VIEWED_PRODUCTS;
    private final int MAX_SIZE = 3;

    public Queue<Product> getVIEWED_PRODUCTS() {
        return VIEWED_PRODUCTS;
    }

    public ViewedProducts(){
        this.VIEWED_PRODUCTS = new ArrayDeque<>();
    }

    public void delete(){
        if(VIEWED_PRODUCTS.size() != 0)
            VIEWED_PRODUCTS.remove();
    }

    public void add(Product item){

        if(item == null)
            throw new IllegalArgumentException();

        if(VIEWED_PRODUCTS.contains(item)){
            VIEWED_PRODUCTS.remove(item);
            VIEWED_PRODUCTS.offer(item);
        }
        else if(VIEWED_PRODUCTS.size() == MAX_SIZE){
            this.delete();
            VIEWED_PRODUCTS.offer(item);
        }
        else VIEWED_PRODUCTS.offer(item);

    }
}
