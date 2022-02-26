package com.es.phoneshop.web;

import com.es.phoneshop.order.ArrayListOrderDao;
import com.es.phoneshop.order.Order;
import com.es.phoneshop.order.OrderDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderDao = ArrayListOrderDao.getInstance();
    }

    private void orderNotExistCase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setStatus(404);
        if (request.getPathInfo() != null) {
            request.setAttribute("id", request.getPathInfo().substring(1));
        } else {
            request.setAttribute("id", "");
        }
        request.getRequestDispatcher("/WEB-INF/pages/errorOrderNotFound.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() != null) {
            String secureOrderId = request.getPathInfo().substring(1);
            Optional<Order> order = orderDao.getOrderBySecureId(secureOrderId);
            if (order.isPresent()) {
                request.setAttribute("order", order.get());
                request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
            } else {
                orderNotExistCase(request, response);
            }
        } else {
            orderNotExistCase(request, response);
        }
    }
}

