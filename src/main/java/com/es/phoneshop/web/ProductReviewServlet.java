package com.es.phoneshop.web;

import com.es.phoneshop.core.dao.product.ArrayListProductDao;
import com.es.phoneshop.core.dao.product.ProductDao;
import com.es.phoneshop.core.dao.productReview.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductReviewServlet extends HttpServlet {
    protected static final String PRODUCT_REVIEWS = "productReviews";

    private ProductReviewService productReviewService;
    private ProductReviewDao productReviewDao;
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productReviewService = ProductReviewServiceImpl.getInstance();
        productReviewDao = HashMapProductReviewDao.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = getProductId(req);
        productReviewDao.findProductReviewsByProductId(productId)
                .ifPresent(productReviews -> req.setAttribute(PRODUCT_REVIEWS, productReviews));
        req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductReview review = productReviewService.createReview(req);
        if (review.getRating() < 0 || review.getRating() > 5) {
            req.setAttribute("errorRating", true);
            req.setAttribute("product", productDao.getProduct(getProductId(req)));
            doGet(req, resp);
        } else {
            productReviewDao.addProductReview(review);
            resp.sendRedirect(req.getServletContext().getContextPath() + "/products/" + review.getProductId());
        }
    }

    private Long getProductId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return Long.valueOf(pathInfo.substring(1));
    }
}
