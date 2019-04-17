package com.es.phoneshop.web;

import com.es.phoneshop.core.dao.productReview.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ModerateReviewsPageServlet extends HttpServlet {
    protected final static String REVIEWS = "reviews";
    protected final static String REVIEW_ID = "reviewId";
    private ProductReviewDao productReviewDao;
    private ProductReviewService productReviewService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productReviewDao = HashMapProductReviewDao.getInstance();
        productReviewService = ProductReviewServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductReview> allReviews = productReviewDao.findAllProductReviews();
        req.setAttribute(REVIEWS, allReviews);
        req.getRequestDispatcher("/WEB-INF/pages/moderateReviews.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long reviewId = Long.valueOf(req.getParameter(REVIEW_ID));
        productReviewService.approveProductReview(reviewId);
        resp.sendRedirect(req.getRequestURI());
    }
}
