package com.es.phoneshop.web;

import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OverviewPageServlet extends HttpServlet {

    private OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = getOrderId(request);
        Order order = orderService.getOrder(id);
        if (order == null) {
            response.sendError(404);
            return;
        }
        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/pages/overviewPage.jsp").forward(request, response);
    }

    private String getOrderId(HttpServletRequest request) {
        return request.getPathInfo().substring(1);
    }
}
