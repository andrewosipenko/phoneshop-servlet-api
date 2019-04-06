package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartItem;
import com.es.phoneshop.model.product.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.history.HttpSessionHistoryService;
import com.es.phoneshop.web.helper.Error;
import com.es.phoneshop.web.helper.ProductDetailsErrorHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    protected static final String ID = "id";
    protected static final String PRODUCT = "product";
    protected static final String QUANTITY = "quantity";
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private ProductDetailsErrorHandler errorHandler;
    private HttpSessionHistoryService historyService;
    private HttpSessionCartService httpSessionCartService;


    @Override
    public void init(ServletConfig config) {
        errorHandler = new ProductDetailsErrorHandler();
        historyService = HttpSessionHistoryService.getInstance();
        httpSessionCartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        errorHandler.handle(req);
        Long productId = getProductId(req);
        req.setAttribute(ID, productId);
        req.setAttribute(PRODUCT, productDao.getProduct(productId));
        historyService.update(req.getSession(), productId);
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

        resp.sendRedirect(req.getRequestURI() + ("?err=" + errorType.getErrorCode()));
    }

    private Long getProductId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return Long.valueOf(pathInfo.substring(1));
    }
}

