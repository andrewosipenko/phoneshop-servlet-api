package com.es.phoneshop.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class OrderOverviewPageServlet extends AbstractServlet {
    private static final String ORDER_OVERVIEW_JSP_PATH = "/WEB-INF/pages/orderOverview.jsp";
    private static final String ORDER = "order";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secureId = request.getPathInfo().substring(1);
        request.setAttribute(ORDER, orderDao.getOrderBySecureId(secureId));
        request.getRequestDispatcher(ORDER_OVERVIEW_JSP_PATH).forward(request, response);
    }
}
