package com.es.phoneshop.core.dao.productReview;

import java.util.*;

public class HashMapProductReviewDao implements ProductReviewDao {

    private static HashMapProductReviewDao instance;
    private final Map<Long, List<ProductReview>> productReviews;

    private HashMapProductReviewDao() {
        productReviews = new HashMap<>();
    }

    public static HashMapProductReviewDao getInstance() {
        if (instance == null) {
            synchronized (HashMapProductReviewDao.class) {
                if (instance == null) {
                    instance = new HashMapProductReviewDao();
                }
            }
        }
        return instance;
    }

    @Override
    public void addProductReview(ProductReview productReview) {
        List<ProductReview> reviews = productReviews.get(productReview.getProductId());
        if (reviews == null) {
            reviews = new ArrayList<>();
            reviews.add(productReview);
            productReviews.put(productReview.getProductId(), reviews);
        } else {
            reviews.add(productReview);
            productReviews.put(productReview.getProductId(), reviews);
        }
    }

    @Override
    public Optional<List<ProductReview>> findProductReviewsByProductId(Long productId) {
        return Optional.ofNullable(productReviews.get(productId));
    }

    @Override
    public Optional<ProductReview> findProductReviewById(Long reviewId) {
        return findAllProductReviews()
                .stream()
                .filter(productReview -> productReview.getReviewId().equals(reviewId))
                .findAny();
    }

    @Override
    public List<ProductReview> findAllProductReviews() {
        Collection<List<ProductReview>> values = productReviews.values();
        List<ProductReview> allReviews = new LinkedList<>();
        for (List<ProductReview> list : values) {
            allReviews.addAll(list);
        }
        return allReviews;
    }
}
