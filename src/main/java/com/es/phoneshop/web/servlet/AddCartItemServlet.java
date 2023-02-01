package com.es.phoneshop.web.servlet;

import com.es.phoneshop.util.CartItemQuantityValidationUtil;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.HttpSessionCartService;
import com.es.phoneshop.exception.OutOfStockException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCartItemServlet extends HttpServlet {
    private static final long serialVersionUID = 2812860408953942622L;

    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productInfo = request.getPathInfo().substring(1);
        Long productId = Long.valueOf(productInfo);
        String quantityString = request.getParameter("quantity" + productInfo);


        String errorMessage = CartItemQuantityValidationUtil
                .validateQuantity(request, quantityString);

        if (errorMessage != null) {
            incorrectQuantityError(request, response, errorMessage, productId, quantityString);
            return;
        }

        int quantity = CartItemQuantityValidationUtil.parseQuantity(request, quantityString).intValue();
        try {
            cartService.add(productId, quantity, request);
        } catch (OutOfStockException e) {
            incorrectQuantityError(request, response, "Out of stock, available " + e.getStock(), productId, quantityString);
            return;
        }

        request.setAttribute("message", "Product added to cart");
        response.sendRedirect(request.getContextPath() + "/products?message=Product added to cart");
    }

    private void incorrectQuantityError(HttpServletRequest request, HttpServletResponse response,
                                        String errorMessage, Long productId, String quantityString) throws IOException {
        request.setAttribute("error", errorMessage);
        request.setAttribute("errorProductId", productId);
        request.setAttribute("errorQuantity", quantityString);
        response.sendRedirect(request.getContextPath() + "/products?error=" + errorMessage + "&errorProductId=" + productId + "&errorQuantity=" + quantityString);
    }
}
