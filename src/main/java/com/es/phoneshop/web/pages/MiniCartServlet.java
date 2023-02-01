package com.es.phoneshop.web.pages;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class MiniCartServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BigDecimal totalCost = cartService.getCart(request).getTotalCost();
        int totalQuantity = cartService.getCart(request).getTotalQuantity();

        request.setAttribute("totalQuantity", totalQuantity);
        request.setAttribute("totalCost", totalCost);
        request.getRequestDispatcher("/WEB-INF/pages/miniCart.jsp").include(request, response);
    }
}
