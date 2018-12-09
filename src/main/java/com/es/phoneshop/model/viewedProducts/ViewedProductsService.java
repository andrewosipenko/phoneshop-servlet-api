package com.es.phoneshop.model.viewedProducts;

import com.es.phoneshop.model.product.Product;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface ViewedProductsService {

    List<Product> getViewedProducts(HttpSession session);

    void addToViewedProducts(List<Product> list, Product product);

}
