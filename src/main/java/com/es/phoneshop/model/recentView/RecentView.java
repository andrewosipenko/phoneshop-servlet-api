package com.es.phoneshop.model.recentView;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayDeque;
import java.util.Deque;

public class RecentView {

    private Deque<Product> deque;

    public RecentView() {
        deque = new ArrayDeque<>();
    }

    public void add(Product product) {
        if(deque.contains(product)) {
            deque.remove(product);
            deque.addFirst(product);
            return;
        }
        if(deque.size() == 3) {
            deque.removeLast();
        }
        deque.addFirst(product);
    }

    public Deque<Product> getDeque() {
        return deque;
    }
}
