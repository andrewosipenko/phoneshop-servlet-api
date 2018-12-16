package com.es.phoneshop.web;

import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        orderDao = ArrayListOrderDao.getInstance();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        int idIndex = uri.lastIndexOf("/");
        String stringId = uri.substring(idIndex + 1);
        Long id = Long.parseLong(stringId);
        Order order = orderDao.getOrder(id);
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(req, resp);
    }
}
