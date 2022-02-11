package com.es.phoneshop.model.recentView;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Lock;

public class RecentView implements Serializable {
    private final Deque<Product> deque;

    private final Lock lock;

    public RecentView(Lock lock) {
        deque = new ConcurrentLinkedDeque<>();
        this.lock = lock;
    }

    public void add(Product product) {
        lock.lock();
        try {
            if (deque.contains(product)) {
                deque.remove(product);
                deque.addFirst(product);
                return;
            }
            if (deque.size() == 3) {
                deque.removeLast();
            }
            deque.addFirst(product);
        } finally {
            lock.unlock();
        }
    }

    public Deque<Product> getDeque() {
        return deque;
    }
}
