package com.es.phoneshop.core.dao.productReview;

import javax.servlet.http.HttpServletRequest;

public interface ProductReviewService {
    ProductReview createReview(HttpServletRequest request);

    void approveProductReview(Long reviewId);
}
