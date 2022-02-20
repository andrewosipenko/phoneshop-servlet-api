package com.es.phoneshop.web;

import com.es.phoneshop.order.ArrayListOrderDao;
import com.es.phoneshop.order.OrderDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderDao = ArrayListOrderDao.getInstance();
    }

    private boolean isSecureOrderIdExist(String orderId) {
        return orderDao.getOrderBySecureId(orderId).isPresent();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo()!= null && isSecureOrderIdExist(request.getPathInfo().substring(1))) {
            String secureOrderId = request.getPathInfo().substring(1);
            request.setAttribute("order", orderDao.getOrderBySecureId(secureOrderId).get());
            request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
        } else {
            response.setStatus(404);
            if (request.getPathInfo() != null) {
                request.setAttribute("id", request.getPathInfo().substring(1));
            } else {
                request.setAttribute("id", "");
            }
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
        }
    }
}

