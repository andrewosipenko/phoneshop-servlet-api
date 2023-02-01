package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.OrderDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.model.product.service.OrderService;
import com.es.phoneshop.model.product.service.impl.OrderServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OverviewPageServlet extends HttpServlet {
    private OrderDao orderDao;
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = OrderServiceImpl.getInstance();
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("order",
                orderDao.getOrderBySecureId(orderService.parseSecureOrderIdFromRequest(request)));
        request.getRequestDispatcher("/WEB-INF/pages/overview.jsp").forward(request, response);
    }
}
