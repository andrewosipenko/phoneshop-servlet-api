package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.CartServiceImpl;
import com.es.phoneshop.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        cartService = CartServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        req.setAttribute("cart", cartService.getCart(httpServletRequest.getSession()));
        req.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        String name = request.getParameter("name");
        String deliveryAddress = request.getParameter("deliveryAddress");
        String phone = request.getParameter("phone");

        Order order = orderService.placeOrder(cart, name, deliveryAddress, phone);
        cartService.clearCart(cart);
        response.sendRedirect(request.getContextPath() + "/orderOverview/" + order.getId());
    }
}
