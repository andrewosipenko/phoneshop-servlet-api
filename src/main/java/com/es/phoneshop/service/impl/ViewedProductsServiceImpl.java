package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ViewedProductsList;
import com.es.phoneshop.service.ViewedProductsService;
import org.apache.commons.collections4.queue.CircularFifoQueue;

public class ViewedProductsServiceImpl implements ViewedProductsService {

    private static volatile ViewedProductsServiceImpl viewedProducts;

    private ViewedProductsServiceImpl(){}

    public static ViewedProductsServiceImpl getInstance(){
        if(viewedProducts == null)
            synchronized (CartServiceImpl.class){
                if(viewedProducts == null)
                    viewedProducts = new ViewedProductsServiceImpl();
            }
        return viewedProducts;
    }

    @Override
    public void addToViewed(Product viewedProduct, ViewedProductsList productList) {
        CircularFifoQueue<Product> products = productList.getViewedProducts();
        products.removeIf(viewedProduct::equals);
        products.add(viewedProduct);
    }
}
