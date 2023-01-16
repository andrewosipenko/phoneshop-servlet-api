package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RecentlyViewedProduct {
    List<Product> getRecentlyViewedProducts(HttpServletRequest request);

    void addViewedProduct(List<Product> recentlyViewedProducts, Long productId);
}
