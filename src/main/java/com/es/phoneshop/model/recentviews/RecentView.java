package com.es.phoneshop.model.recentviews;

import com.es.phoneshop.model.product.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RecentView {
    private List<Product> recentlyViewed;

    public RecentView() {
        this.recentlyViewed = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "RecentlyViewed[" + recentlyViewed + "]";
    }
}
