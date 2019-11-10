package com.es.phoneshop.model.recentlyViewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public class HttpSessionViewedProductsService implements ViewedProductsService {
    private static ViewedProductsService viewedProductsService;

    private HttpSessionViewedProductsService(){
    }

    public static ViewedProductsService getInstance(){
        if (viewedProductsService==null){
            synchronized (ViewedProductsService.class){
                if (viewedProductsService==null) {
                    viewedProductsService = new HttpSessionViewedProductsService();
                }
            }
        }
        return viewedProductsService;
    }

    @Override
    public RecentlyViewedProducts getViewedProducts(HttpServletRequest request) {
        RecentlyViewedProducts viewedProducts=(RecentlyViewedProducts)
                request.getSession().getAttribute("viewedProducts");
        if (viewedProducts==null){
            viewedProducts=new RecentlyViewedProducts();
            request.getSession().setAttribute("viewedProducts",viewedProducts);
        }
        return viewedProducts;
    }

    @Override
    public void add(RecentlyViewedProducts viewedProducts,Product product) {
        if (!viewedProducts.getViewedProducts().contains(product)){
            if (viewedProducts.getViewedProducts().size()<3) {
                viewedProducts.addViewedProduct(product);
            }
            else{
                viewedProducts.getViewedProducts().remove(0);
                viewedProducts.addViewedProduct(product);
            }
        }
    }
}
