package com.es.phoneshop.web;

import com.es.phoneshop.order.Order;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.DefaultOrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() {
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secureId = request.getPathInfo().split("/")[1];
        Order order = orderService.getBySecureId(secureId);

        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
    }
}
