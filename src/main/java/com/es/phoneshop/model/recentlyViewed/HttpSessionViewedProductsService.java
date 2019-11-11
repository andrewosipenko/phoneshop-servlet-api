package com.es.phoneshop.model.recentlyViewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class HttpSessionViewedProductsService implements ViewedProductsService {
    public static final String VIEWED_PRODUCTS = "viewedProducts";
    private static ViewedProductsService viewedProductsService;

    private HttpSessionViewedProductsService() {
    }

    public static ViewedProductsService getInstance() {
        if (viewedProductsService == null) {
            synchronized (ViewedProductsService.class) {
                if (viewedProductsService == null) {
                    viewedProductsService = new HttpSessionViewedProductsService();
                }
            }
        }
        return viewedProductsService;
    }

    @Override
    public RecentlyViewedProducts getViewedProducts(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        RecentlyViewedProducts viewedProducts = (RecentlyViewedProducts)
                httpSession.getAttribute(VIEWED_PRODUCTS);
        if (viewedProducts == null) {
            viewedProducts = new RecentlyViewedProducts();
            httpSession.setAttribute(VIEWED_PRODUCTS, viewedProducts);
        }
        return viewedProducts;
    }

    @Override
    public void add(RecentlyViewedProducts viewedProducts, Product product) {
        List<Product> viewedProductsList=viewedProducts.getViewedProducts();
        if (!viewedProductsList.contains(product)) {
            if (viewedProductsList.size() > 2) {
                viewedProducts.deleteViewedProduct(0);
            }
            viewedProducts.addViewedProduct(product);
        }
    }
}
