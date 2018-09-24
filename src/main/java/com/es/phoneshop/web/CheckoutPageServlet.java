package com.es.phoneshop.web;

import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.CartService;
import com.es.phoneshop.model.CartServiceImpl;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService = CartServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("totalCost", sumTotalCost(request));
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = orderService.placeOrder(cartService.getCart(request));

        order.setName(request.getParameter("Name"));
        order.setAddress(request.getParameter("Address"));
        order.setPhone(request.getParameter("Phone"));
        order.setTotalCost(sumTotalCost(request));

        request.getSession().removeAttribute("cart");
        response.sendRedirect(request.getRequestURI() + "/" + order.getId());
    }

    private int sumTotalCost(HttpServletRequest request) {
        int totalCost = 0;
        for(CartItem cartItem : cartService.getCart(request).getCartItems()) {
            totalCost += cartItem.getQuantity() * cartItem.getProduct().getPrice().intValue();
        }
        return totalCost;
    }
}
