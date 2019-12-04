package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.reviews.DefaultReviewService;
import com.es.phoneshop.model.reviews.Review;
import com.es.phoneshop.model.reviews.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductReviewServlet extends HttpServlet {
    private ProductDao productDao;
    private ReviewService reviewService;

    private static final String NAME = "name";
    private static final String RATING = "rating";
    private static final String COMMENT = "comment";
    private static final String ERROR_MAP = "errorMap1";
    private static final String REVIEW = "review";

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        reviewService = DefaultReviewService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long productID = getProductID(request);
        Product product = productDao.getProduct(productID);

        Map<String, String> errorMap = new HashMap<>();
        Review review = new Review();

        String name = getRequiredParameter(request, NAME, errorMap);
        String comment = getRequiredParameter(request, COMMENT, errorMap);
        String ratingString = getRequiredParameter(request, RATING, errorMap);
        Integer rating = validateRating(ratingString, errorMap);

        if (!errorMap.isEmpty()) {
            request.setAttribute(ERROR_MAP, errorMap);
        } else {
            setReviewParams(review, name, rating, comment);
            reviewService.add(product, review);
            request.setAttribute(REVIEW, review);
        }
        response.sendRedirect(request.getContextPath() + "/products/" + productID);
    }

    private void setReviewParams(Review review, String name, Integer rating, String comment) {
        review.setName(name);
        review.setComment(comment);
        review.setRating(rating);
    }

    private Integer validateRating(String rating, Map<String, String> errorMap) {
        Integer result = null;
        try {
            result = Integer.valueOf(rating);
            if (result <= 0 || result > 5) {
                errorMap.put(rating, "invalid rating");
                return null;
            }
        } catch (Exception e) {
            errorMap.put(rating, "invalid rating");
            return null;
        }
        return result;
    }

    private String getRequiredParameter(HttpServletRequest request, String name, Map<String, String> errorMap) {
        String result = request.getParameter(name);
        if (result == null || result.isEmpty()) {
            errorMap.put(name, name + " is required");
        }
        return result;
    }

    private long getProductID(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String productID = uri.substring(uri.lastIndexOf("/") + 1);
        return Long.parseLong(productID);
    }
}
