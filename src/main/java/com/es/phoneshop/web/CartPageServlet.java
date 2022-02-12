package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.*;
import com.es.phoneshop.model.recentView.HttpSessionRecentViewService;
import com.es.phoneshop.model.recentView.RecentView;
import com.es.phoneshop.model.recentView.RecentViewService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


public class CartPageServlet extends HttpServlet {
    private RecentViewService recentViewService;
    private CartService cartService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Map<Long, String> errors = new HashMap<>();
            String[] productIds = request.getParameterValues("productId");
            String[] quantities = request.getParameterValues("quantity");
            for (int i = 0; i < productIds.length; i++) {
                int quantity;
                long productId = 0;
                try {
                    productId = Long.parseLong(productIds[i]);
                    NumberFormat format = NumberFormat.getInstance(request.getLocale());
                    quantity = format.parse(quantities[i]).intValue();
                    if (quantity < 1) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException | ParseException e) {
                    errors.put(productId, "Invalid quantity");
                    continue;
                }
                try {
                    cartService.update(cartService.getCart(request), productId, quantity, request.getSession());
                } catch (OutOfStockException e) {
                    errors.put(productId, "not enough stock available");
                }
            }
            if(errors.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart?message=Cart updated successfully");
            } else {
                request.setAttribute("errors", errors);
                doGet(request,response);
            }
        } catch (NullPointerException e) {
            doGet(request, response);
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        recentViewService = HttpSessionRecentViewService.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = HttpSessionCartService.getInstance().getCart(request);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }
}
