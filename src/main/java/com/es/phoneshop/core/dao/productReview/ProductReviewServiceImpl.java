package com.es.phoneshop.core.dao.productReview;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ProductReviewServiceImpl implements ProductReviewService {

    private static ProductReviewServiceImpl instance;

    private ProductReviewDao productReviewDao;

    private ProductReviewServiceImpl() {
        productReviewDao = HashMapProductReviewDao.getInstance();
    }

    public static ProductReviewServiceImpl getInstance() {
        if (instance == null) {
            synchronized (ProductReviewServiceImpl.class) {
                if (instance == null) {
                    instance = new ProductReviewServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public ProductReview createReview(HttpServletRequest request) {
        String name = request.getParameter("name");
        Short rating = Short.valueOf(request.getParameter("rating"));
        String comment = request.getParameter("comment");
        Long productId = Long.valueOf(request.getParameter("productId"));
        Long reviewId = (long) productReviewDao.findAllProductReviews().size();
        ProductReview productReview = new ProductReview(name, rating, comment, productId);
        productReview.setReviewId(reviewId);
        return productReview;
    }

    @Override
    public void approveProductReview(Long reviewId) {
        Optional<ProductReview> productReviewById = productReviewDao.findProductReviewById(reviewId);
        productReviewById.ifPresent(productReview -> productReview.setApproved(true));
    }
}
