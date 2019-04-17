package com.es.phoneshop.web;

import com.es.phoneshop.core.cart.Cart;
import com.es.phoneshop.core.cart.CartItem;
import com.es.phoneshop.core.cart.CartService;
import com.es.phoneshop.core.cart.HttpSessionCartService;
import com.es.phoneshop.core.dao.product.ArrayListProductDao;
import com.es.phoneshop.core.dao.product.ProductDao;
import com.es.phoneshop.core.dao.productReview.HashMapProductReviewDao;
import com.es.phoneshop.core.dao.productReview.ProductReviewDao;
import com.es.phoneshop.core.exceptions.OutOfStockException;
import com.es.phoneshop.core.history.HistoryService;
import com.es.phoneshop.core.history.HttpSessionHistoryService;
import com.es.phoneshop.web.helper.Error;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    protected static final String PRODUCT_REVIEWS = "productReviews";
    protected static final String ID = "id";
    protected static final String PRODUCT = "product";
    protected static final String QUANTITY = "quantity";
    private ProductDao productDao;
    private HistoryService historyService;
    private CartService httpSessionCartService;
    private ProductReviewDao productReviewDao;

    @Override
    public void init(ServletConfig config) {
        productDao = ArrayListProductDao.getInstance();
        historyService = HttpSessionHistoryService.getInstance();
        httpSessionCartService = HttpSessionCartService.getInstance();
        productReviewDao = HashMapProductReviewDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = getProductId(req);
        req.setAttribute(ID, productId);
        req.setAttribute(PRODUCT, productDao.getProduct(productId));
        historyService.update(req.getSession(), productId);
        productReviewDao.findProductReviewsByProductId(productId)
                .ifPresent(productReviews -> req.setAttribute(PRODUCT_REVIEWS, productReviews));
        req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Error errorType;
        try {
            Integer quantity = Integer.valueOf(req.getParameter(QUANTITY));
            if (quantity < 1) {
                throw new IllegalArgumentException();
            }
            Long productId = getProductId(req);
            Cart customerCart = httpSessionCartService.getCart(req);
            try {
                httpSessionCartService.add(req, customerCart, new CartItem(productId, quantity));
                resp.sendRedirect(req.getRequestURI() + "?productAdded=ok");
                return;
            } catch (OutOfStockException e) {
                errorType = Error.OUT_OF_STOCK;
            }
        } catch (IllegalArgumentException e) {
            errorType = Error.PARSE_ERROR;
        }

        req.setAttribute("error", errorType.getErrorMessage());
        doGet(req, resp);
    }

    private Long getProductId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return Long.valueOf(pathInfo.substring(1));
    }
}

