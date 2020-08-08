package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.HttpServletCartService;
import com.es.phoneshop.model.product.service.ProductService;
import com.es.phoneshop.model.product.service.ProductServiceImpl;
import com.es.phoneshop.model.recentlyViewed.HttpServletRecentlyViewedService;
import com.es.phoneshop.model.recentlyViewed.RecentlyViewedService;
import com.es.phoneshop.web.constants.ControllerConstants;
import com.es.phoneshop.web.exceptions.OutOfStockException;
import com.es.phoneshop.web.constants.PostParamKeys;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductService productService;
    private CartService<HttpServletRequest> cartService;
    private RecentlyViewedService<HttpServletRequest> recentlyViewedService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = ProductServiceImpl.INSTANCE;
        cartService = HttpServletCartService.INSTANCE;
        recentlyViewedService = HttpServletRecentlyViewedService.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInto = Optional.ofNullable(request.getPathInfo())
                .orElse(" ");

        var product = productService.getProduct(pathInto);
        request.setAttribute("product", product);
        recentlyViewedService.add(recentlyViewedService.getList(request), product);
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("recentlyViewed", recentlyViewedService.getList(request));

        if (pathInto.contains("priceHistory")) {
            request.getRequestDispatcher(ControllerConstants.PRICE_HISTORY_JSP_PATH).forward(request, response);
        } else {
            request.getRequestDispatcher(ControllerConstants.PRODUCT_DETAILS_JSP_PATH).forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInto = Optional.ofNullable(request.getPathInfo())
                .orElse(" ");
        String quantityParam = Optional.ofNullable(request.getParameter(String.valueOf(PostParamKeys.quantity)))
                .orElse(" ");


        //imho it's bad, i'll think how to implement it better
        //? maybe move try-catch block into separate methods?
        int quantity;

        try {
            NumberFormat numberFormat = NumberFormat.getInstance(request.getLocale());
            quantity = numberFormat.parse(quantityParam).intValue();
        } catch (ParseException e) {
            response.sendRedirect(request.getContextPath() + "/products/" + parseId(pathInto)
                    + "?error=" + ControllerConstants.NOT_A_NUMBER_ERROR_MESSAGE);
            return;
        }

        try {
            cartService.add(cartService.getCart(request), parseId(pathInto), quantity);
        } catch (OutOfStockException e) {
            response.sendRedirect(request.getContextPath() + "/products/" + parseId(pathInto)
                    + "?error=" + ControllerConstants.OUT_OF_STOCK_ERROR_MESSAGE);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/products/" + parseId(pathInto)
                + "?message=" + ControllerConstants.ADDING_TO_CART_SUCCESS_MESSAGE);
    }

    private long parseId(String pathInfo) {
        try {
            return Integer.parseInt(pathInfo.split("/")[1]);
        } catch (NumberFormatException e) {
            throw new NoSuchElementException(pathInfo.split("/")[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchElementException(" ");
        }
    }
}
