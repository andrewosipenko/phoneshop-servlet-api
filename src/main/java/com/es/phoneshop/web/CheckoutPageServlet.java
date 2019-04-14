package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.order.ArrayListOrderDao;
import com.es.phoneshop.model.product.dao.order.Order;
import com.es.phoneshop.model.product.dao.order.OrderDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {

    private OrderDao orderDao;

    @Override
    public void init(ServletConfig config) {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Order order = orderDao.getOrder(req);
        if (orderDao.isOrderValid(order)) {
            orderDao.placeOrder(order);
            resp.sendRedirect(req.getContextPath() + "/order/overview/" + order.getId());
        } else {
            req.setAttribute("error", true);
            doGet(req, resp);
        }
    }
}
