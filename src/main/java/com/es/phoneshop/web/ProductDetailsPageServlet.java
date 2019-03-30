package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.history.HttpHistoryService;
import com.es.phoneshop.web.helper.Error;
import com.es.phoneshop.web.helper.ProductDetailsErrorHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao = ArrayListProductDao.getInstance();
    private ProductDetailsErrorHandler errorHandler;
    private HttpHistoryService historyService;

    protected static final String ID = "id";
    protected static final String PRODUCT = "product";
    protected static final String QUANTITY = "quantity";

    @Override
    public void init(ServletConfig config) {
        errorHandler = new ProductDetailsErrorHandler();
        historyService = HttpHistoryService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        errorHandler.handle(req);
        Long productId = getProductId(req);
        req.setAttribute(ID, productId);
        req.setAttribute(PRODUCT, productDao.getProduct(productId));
        historyService.update(req, productId);
        req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Error errorType = null;
        Integer quantity = null;
        try {
            quantity = Integer.valueOf(req.getParameter(QUANTITY));
        } catch (NumberFormatException e) {
            errorType = Error.PARSE_ERROR;
        }
        if (quantity != null) {
            Long productId = getProductId(req);
            CartService httpSessionCartService = HttpSessionCartService.getInstance();
            Cart customerCart = httpSessionCartService.getCart(req);

            try {
                httpSessionCartService.add(customerCart, productId, quantity);
                req.getServletContext().setAttribute("cart", customerCart);
                resp.sendRedirect(req.getRequestURI() + "?productAdded=ok");
                return;
            } catch (OutOfStockException e) {
                errorType = Error.OUT_OF_STOCK;
            }

        }
        resp.sendRedirect(req.getRequestURI() + ("?err=" + errorType.getErrorCode()));
    }

    private Long getProductId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return Long.valueOf(pathInfo.substring(1));
    }
}
