package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends AbstractServlet {
    private static final String CART_JSP_PATH = "/WEB-INF/pages/cart.jsp";
    private static final String CART_ITEMS = "cartItems";
    private static final String PRODUCT_ID = "productId";
    private static final String QUANTITY = "quantity";
    private static final String ERROR = "error";
    private static final String ERRORS = "errors";
    private static final String SUCCESSFULLY_UPDATE_MESSAGE = "Cart updated successfully";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(CART_ITEMS, cartService.getCart(request).getCartItems());
        request.getRequestDispatcher(CART_JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues(PRODUCT_ID);
        String[] quantities = request.getParameterValues(QUANTITY);
        Map<Long, String> errors = new HashMap<>();
        for (int i = 0; i < productIds.length; i++) {
            Long productId = Long.valueOf(productIds[i]);
            try {
                int quantity = validateQuantityInput(request, quantities[i]);
                Cart cart = cartService.getCart(request);
                cartService.update(productId, quantity, cart);
            } catch (ParseException | NumberFormatException | OutOfStockException e) {
                errors.put(productId, e.getMessage());
                request.setAttribute(ERROR, e.getMessage());
            }
        }
        if(errors.isEmpty()) {
            response.sendRedirect(String.format("%s/cart?message=%s",
                    request.getContextPath(), SUCCESSFULLY_UPDATE_MESSAGE));
        } else {
            request.setAttribute(ERRORS, errors);
            doGet(request, response);
        }
    }
}
