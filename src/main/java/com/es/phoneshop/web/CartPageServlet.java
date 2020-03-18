package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.impl.DefaultCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private DefaultCartService cartService;

    @Override
    public void init(ServletConfig config) {
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);

        request.setAttribute("cart", cart);

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] quantityStrings = request.getParameterValues("quantity");
        String[] productIdStrings = request.getParameterValues("productId");

        Cart cart = cartService.getCart(request);
        Locale locale = request.getLocale();
        Map<Long, String> errorMap = new HashMap<>();

        for (int i = 0; i < productIdStrings.length; i++) {
            long productId = Long.parseLong(productIdStrings[i]);
            NumberFormat numberFormat = NumberFormat.getInstance(locale);
            try {
                int quantity = numberFormat.parse(quantityStrings[i]).intValue();
                cartService.update(cart, productId, quantity);
            } catch (ParseException e) {
                errorMap.put(productId, "Not a number");
            } catch (OutOfStockException e) {
                errorMap.put(productId, "Not enough stock, available " + e.getStockAvailable());
            }
        }
        if (errorMap.isEmpty()) {
            response.sendRedirect(request.getRequestURI() + "?success=true");
        } else {
            request.setAttribute("errorMap", errorMap);
            doGet(request, response);
        }
    }
}
