package com.es.phoneshop.model.product.recentlyview;

import com.es.phoneshop.model.product.productdao.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecentlyViewSection implements Serializable {
    List<Product> recentlyView;

    public RecentlyViewSection() {
        this.recentlyView = new ArrayList<>();
    }

    public List<Product> getRecentlyView() {
        return recentlyView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecentlyViewSection that = (RecentlyViewSection) o;

        return recentlyView.equals(that.recentlyView);
    }

    @Override
    public int hashCode() {
        return recentlyView.hashCode();
    }
}
