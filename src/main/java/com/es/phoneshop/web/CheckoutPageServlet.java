package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.order.Order;
import com.es.phoneshop.order.OrderCreateForm;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.service.impl.DefaultOrderService;

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
    public void init() {
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> errorMap = new HashMap<>();
        Cart cart = cartService.getCart(req);
        Order order = orderService.getOrder(cart);
        OrderCreateForm orderCreateForm = new OrderCreateForm();

        orderCreateForm.setFirstName(req.getParameter("First name"));
        orderCreateForm.setLastName(req.getParameter("Last name"));
        orderCreateForm.setDate(req.getParameter("Date"));
        orderCreateForm.setPaymentMethod(req.getParameter("Payment method"));
        orderCreateForm.setPhone(req.getParameter("Phone"));
        orderCreateForm.setAddress(req.getParameter("Address"));
        order.setOrderCreateForm(orderCreateForm);

        validate(order, errorMap);

        if (!errorMap.isEmpty()) {
            req.setAttribute("order", order);
            req.setAttribute("errorMap", errorMap);
            req.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(req, resp);
            return;
        }
        String secureId = orderService.placeOrder(order);
        cartService.clearCart(cart);
        resp.sendRedirect(req.getContextPath() + "/order/overview/" + secureId);
    }

    private void validate(Order order, HashMap<String, String> errorMap) {
        if (order.getOrderCreateForm().getFirstName() == null || order.getOrderCreateForm().getFirstName().trim().equals("")) {
            errorMap.put("First name", "First name is required");
        }
        if (order.getOrderCreateForm().getLastName() == null || order.getOrderCreateForm().getLastName().trim().equals("")) {
            errorMap.put("Last name", "Last name is required");
        }

        if (order.getOrderCreateForm().getPhone() == null || order.getOrderCreateForm().getPhone().trim().equals("")) {
            errorMap.put("Phone", "Phone is required");
        }

        if (order.getOrderCreateForm().getDate() == null || order.getOrderCreateForm().getDate().trim().equals("")) {
            errorMap.put("Date", "Date is required");
        }

        if (order.getOrderCreateForm().getPaymentMethod() == null || order.getOrderCreateForm().getPaymentMethod().trim().equals("")) {
            errorMap.put("Payment Method", "Payment Method is required");
        }

        if (order.getOrderCreateForm().getAddress() == null || order.getOrderCreateForm().getAddress().trim().equals("")) {
            errorMap.put("Address", "Address is required");
        }
    }
}
