package com.es.phoneshop.model.product;

import javax.servlet.http.HttpSession;
import java.util.ArrayDeque;
import java.util.Deque;

public class HttpSessionViewedProductService implements ViewedProductsService {
    private static final String VIEWED_SESSION_ATTRIBUTE = "viewedProducts";
    private static final int MAX_VIEWED_PRODUCTS_ON_THE_PAGE = 5;
    private static HttpSessionViewedProductService INSTANCE = new HttpSessionViewedProductService();

    private HttpSessionViewedProductService() {}

    public static HttpSessionViewedProductService getInstance() {
        return INSTANCE;
    }

    @Override
    public Deque<Product> getViewedProducts(HttpSession session) {
        Deque<Product> result = (Deque<Product>) session.getAttribute(VIEWED_SESSION_ATTRIBUTE);
        if (result == null) {
            result = new ArrayDeque<>();
            session.setAttribute(VIEWED_SESSION_ATTRIBUTE, result);
        }
        return result;
    }

    @Override
    public void addViewedProducts(Deque<Product> dequeProducts, Product product) {
        boolean dequeHasProductId = dequeProducts
                .stream()
                .anyMatch(p -> p.getId().equals(product.getId()));
        if (dequeHasProductId) {
            dequeProducts.remove(product);
        } else {
            if (dequeProducts.size() == MAX_VIEWED_PRODUCTS_ON_THE_PAGE) {
                dequeProducts.removeLast();
            }
        }
        dequeProducts.addFirst(product);
    }
}
