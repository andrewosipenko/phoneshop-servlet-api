package com.es.phoneshop.model.product;
import org.apache.commons.collections4.queue.CircularFifoQueue;

public class ViewedProductsList {
    private static final int SIZE = 3;
    CircularFifoQueue<Product> viewedProducts = new CircularFifoQueue<>(SIZE);

    public CircularFifoQueue<Product> getViewedProducts() {
        return viewedProducts;
    }

    public void setViewedProducts(CircularFifoQueue<Product> viewedProduct) {
        this.viewedProducts = viewedProduct;
    }

    public void addProduct(Product product){
        viewedProducts.add(product);
    }
}
