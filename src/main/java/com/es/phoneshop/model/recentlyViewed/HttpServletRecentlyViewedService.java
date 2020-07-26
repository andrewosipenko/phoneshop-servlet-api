package com.es.phoneshop.model.recentlyViewed;

import com.es.phoneshop.model.product.entity.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public enum HttpServletRecentlyViewedService implements RecentlyViewedService<HttpServletRequest>{
    INSTANCE;

    private static final int DEFAULT_NUMBER_OF_PRODUCTS = 3;

    private static final String RECENTLY_VIEWED_SESSION_ATTRIBUTE = HttpServletRecentlyViewedService.class.getName() + ".queue";

    @Override
    public List<Product> getList(HttpServletRequest request) {
        synchronized (request.getSession()) {
            List<Product> queue = (LinkedList<Product>) request.getSession().getAttribute(RECENTLY_VIEWED_SESSION_ATTRIBUTE);
            if (queue == null) {
                    request.getSession().setAttribute(RECENTLY_VIEWED_SESSION_ATTRIBUTE, queue = new LinkedList<>());
            }
            return queue;
        }
    }

    @Override
    public void add(List<Product> recentlyViewed, Product product) {
        //should i add non null checking?
        synchronized ( recentlyViewed) {
            if(recentlyViewed.stream().noneMatch(productInQueue -> productInQueue.equals(product))){
                if(recentlyViewed.size() >= DEFAULT_NUMBER_OF_PRODUCTS) {
                    ((Queue) recentlyViewed).poll();
                }
                ((Queue<Product>) recentlyViewed).offer(product);
            }
        }
    }
}
