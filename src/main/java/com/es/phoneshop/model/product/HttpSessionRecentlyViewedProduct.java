package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class HttpSessionRecentlyViewedProduct implements RecentlyViewedProduct {

    private static HttpSessionRecentlyViewedProduct instance;

    private static final String RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE = HttpSessionRecentlyViewedProduct.class.getName();

    private ProductDao productDao;

    public static HttpSessionRecentlyViewedProduct getInstance() {
        if (instance == null) {
            instance = new HttpSessionRecentlyViewedProduct();
        }
        return instance;
    }

    private HttpSessionRecentlyViewedProduct() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public List<Product> getRecentlyViewedProducts(HttpServletRequest request) {
        synchronized (request.getSession()) {
            List<Product> recentlyViewed = (List<Product>) request.getSession().getAttribute(RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE);
            if (recentlyViewed == null) {
                recentlyViewed = new ArrayList<>(3);
                request.getSession().setAttribute(RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE, recentlyViewed);
            }
            return recentlyViewed;
        }
    }

    @Override
    public void addViewedProduct(HttpServletRequest request, Long productId) {
        synchronized (request.getSession()) {
            List<Product> recentlyViewedProducts = getRecentlyViewedProducts(request);
            Optional<Product> product = recentlyViewedProducts.stream().filter(products -> products.getId().equals(productId)).findAny();
            if (product.isPresent()) {
                Collections.swap(recentlyViewedProducts, recentlyViewedProducts.size() - 1, recentlyViewedProducts.indexOf(product.get()));
                return;
            }
            if (recentlyViewedProducts.size() == 3) {
                recentlyViewedProducts.remove(0);
            }
            recentlyViewedProducts.add(productDao.getProduct(productId));
        }
    }


}
