package com.es.phoneshop.model.reviews;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;

public class DefaultReviewService implements ReviewService {
    private static ReviewService reviewService;

    private DefaultReviewService() {
    }

    public static ReviewService getInstance() {
        if (reviewService == null) {
            synchronized (ReviewService.class) {
                if (reviewService == null) {
                    reviewService = new DefaultReviewService();
                }
            }
        }
        return reviewService;
    }

    @Override
    public void add(Product product, Review review) {
        ArrayList<Review> reviewList = product.getReviewArrayList();
        reviewList.add(review);
        product.setReviewArrayList(reviewList);
    }
}
