package com.es.phoneshop.web;

import com.es.phoneshop.model.Order;
import com.es.phoneshop.services.OrderService;
import com.es.phoneshop.services.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = orderService.getBySecureId(getSecureIdFromURI(req));
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/pages/overviewPage.jsp").forward(req, resp);
    }

    private String getSecureIdFromURI(HttpServletRequest request) {
        String[] strings = request.getRequestURI().split("/");
        return strings[strings.length - 1];
    }

}
