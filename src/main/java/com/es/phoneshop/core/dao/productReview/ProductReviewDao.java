package com.es.phoneshop.core.dao.productReview;

import java.util.List;
import java.util.Optional;

public interface ProductReviewDao {

    void addProductReview(ProductReview productReview);

    Optional<List<ProductReview>> findProductReviewsByProductId(Long productId);

    Optional<ProductReview> findProductReviewById(Long reviewId);

    List<ProductReview> findAllProductReviews();
}
