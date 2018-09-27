package com.es.phoneshop.web;


import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.CartService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService = CartService.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        Integer orderSum = getSum(request);
        Order order = orderService.placeOrder(cart, name, address, phone, orderSum);
        response.sendRedirect(request.getContextPath() + "/orderOverview/" + order.getOrderId());
    }


    private Integer getSum(HttpServletRequest request){
        Integer orderSum = 0;
        for(CartItem cartItem : cartService.getCart(request).getCartItems()){
            orderSum += cartItem.getQuantity() * cartItem.getProduct().getPrice().intValue();
        }
        return orderSum;
    }
}