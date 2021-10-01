package com.es.phoneshop.web;

import com.es.phoneshop.model.product.order.ArrayListOrderDao;
import com.es.phoneshop.model.product.order.Order;
import com.es.phoneshop.model.product.order.OrderDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {

    public static final String OVERVIEW_PAGE_JSP = "/WEB-INF/pages/orderOverviewPage.jsp";
    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getPathInfo().substring(1);
        Order order = orderDao.getOrderBySecureId(orderId);
        request.setAttribute("order", order);
        request.getRequestDispatcher(OVERVIEW_PAGE_JSP).forward(request, response);
    }
}