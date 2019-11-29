package com.es.phoneshop.web;

import com.es.phoneshop.model.order.DefaultOrderService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderService orderService;

    private static final String ORDER = "order";

    @Override
    public void init() {
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secureId = getSecureId(request);
        Order order = orderService.getOrder(secureId);

        request.setAttribute(ORDER, order);

        request.getRequestDispatcher("/WEB-INF/pages/overview.jsp").forward(request, response);
    }

    public String getSecureId(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
