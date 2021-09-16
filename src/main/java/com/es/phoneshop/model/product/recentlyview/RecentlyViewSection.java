package com.es.phoneshop.model.product.recentlyview;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class RecentlyViewSection {
    List<Product> recentlyView;

    public RecentlyViewSection() {
        this.recentlyView = new ArrayList<>();
    }

    public List<Product> getRecentlyView() {
        return recentlyView;
    }
}
