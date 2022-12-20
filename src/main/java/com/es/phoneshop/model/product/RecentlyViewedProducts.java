package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class RecentlyViewedProducts implements Serializable {
    private static final String VIEWED_PRODUCTS_SESSION_ATTRIBUTE = RecentlyViewedProducts.class.getName() + ".viewed";
    private final int size = 3;

    private RecentlyViewedProducts() {
    }

    private static class SingletonHelper {
        private static final RecentlyViewedProducts INSTANCE = new RecentlyViewedProducts();
    }

    public static RecentlyViewedProducts getInstance() {
        return RecentlyViewedProducts.SingletonHelper.INSTANCE;
    }

    public List<Product> getProducts(HttpServletRequest request) {
        List<Product> products = (List<Product>) request.getSession().getAttribute(VIEWED_PRODUCTS_SESSION_ATTRIBUTE);
        if (products == null) {
            request.getSession().setAttribute(VIEWED_PRODUCTS_SESSION_ATTRIBUTE, products = new LinkedList<>());
            return products;
        }
        return products;
    }

    public void addProduct(HttpServletRequest request, Product product) {
        LinkedList<Product> products = (LinkedList<Product>) request.getSession()
                .getAttribute(VIEWED_PRODUCTS_SESSION_ATTRIBUTE);
        if (products == null) {
            request.getSession().setAttribute(VIEWED_PRODUCTS_SESSION_ATTRIBUTE, products = new LinkedList<>());
        }
        products.addFirst(product);
        if (products.size() > size) {
            products.removeLast();
        }
    }
}
