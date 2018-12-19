package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.orderService.OrderService;
import com.es.phoneshop.model.orderService.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

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
        req.setAttribute("cart", cartService.getCart(req.getSession()));
        req.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCart(req.getSession());
        String name = req.getParameter("name");
        String deliveryAddress = req.getParameter("deliveryAddress");
        String phone = req.getParameter("phone");

        BigDecimal totalPrice = cart.getTotalPrice();
        req.setAttribute("totalPrice", totalPrice);
        Order order = orderService.placeOrder(cart, name, deliveryAddress, phone);
        cartService.clearCart(cart);
        resp.sendRedirect(req.getContextPath() + "/orderOverview/" + order.getId());
    }
}
