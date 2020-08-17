package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.HttpServletCartService;
import com.es.phoneshop.web.PageUrlHelper;
import com.es.phoneshop.web.constants.ControllerConstants;
import com.es.phoneshop.web.constants.PostParamKeys;
import com.es.phoneshop.web.exceptions.OutOfStockException;

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

public class AddCartItemServlet extends HttpServlet {
    private CartService<HttpServletRequest> cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = HttpServletCartService.INSTANCE;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInto = Optional.ofNullable(request.getPathInfo())
                .orElse(" ");
        String quantityParam = Optional.ofNullable(request.getParameter(String.valueOf(PostParamKeys.quantity)))
                .orElse(" ");
        String productIdParam = Optional.ofNullable(request.getParameter(String.valueOf(PostParamKeys.productId)))
                .orElse(" ");
        String redirectPath = PageUrlHelper.getPageUrl(request.getParameter(String.valueOf(PostParamKeys.redirect)), String.valueOf(parseId(pathInto)));

        int quantity;

        try {
            NumberFormat numberFormat = NumberFormat.getInstance(request.getLocale());
            quantity = numberFormat.parse(quantityParam).intValue();
        } catch (ParseException e) {
            response.sendRedirect(request.getContextPath()
                    + redirectPath
                    + "?productId=" + productIdParam
                    + "&quantity=" + quantityParam
                    + "&error=" + ControllerConstants.NOT_A_NUMBER_ERROR_MESSAGE);
            return;
        }

        try {
            cartService.add(cartService.getCart(request), parseId(pathInto), quantity);
        } catch (OutOfStockException e) {
            response.sendRedirect(request.getContextPath()
                    + redirectPath
                    + "?productId=" + productIdParam
                    + "&quantity=" + quantityParam
                    + "&error=" + ControllerConstants.OUT_OF_STOCK_ERROR_MESSAGE);
            return;
        }

        response.sendRedirect(request.getContextPath()
                + redirectPath
                + "?productId=" + productIdParam
                + "&quantity=" + quantityParam
                + "&message=" + ControllerConstants.ADDING_TO_CART_SUCCESS_MESSAGE);
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
