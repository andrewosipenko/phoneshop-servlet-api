package com.es.phoneshop.web;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Customer;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.OrderService;
import com.es.phoneshop.services.impl.CartServiceImpl;
import com.es.phoneshop.services.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

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
        Cart cart = cartService.getCart(req);
        req.setAttribute("cart", cart);
        BigDecimal deliveryCost = orderService.getDeliveryCost();
        req.setAttribute("deliveryCost", deliveryCost);
        req.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(req, resp);
    }

    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCart(req);
        Customer customer = saveCustomer(req);
        HashMap additionalInformation = getAdditionalInformation(req);
        Order order = orderService.placeOrder(cart, customer, additionalInformation);
        orderService.save(order);
        orderService.getOrderById(0L);
        orderService.getBySecureId(order.getSecureId());
        cartService.clearCart(cart);
        resp.sendRedirect(req.getContextPath() + "/order/overview/" + order.getSecureId());
    }

    private HashMap getAdditionalInformation(HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        hashMap.put("deliveryDate", request.getParameter("deliveryDate"));
        hashMap.put("deliveryAddress", request.getParameter("deliveryAddress"));
        hashMap.put("paymentMethod", request.getParameter("paymentMethod"));
        return hashMap;
    }

    private Customer saveCustomer(HttpServletRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getParameter("firstName"));
        customer.setLastName(request.getParameter("secondName"));
        customer.setPhoneNumber(request.getParameter("phoneNumber"));
        return customer;
    }
}
