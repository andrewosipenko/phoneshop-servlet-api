package com.es.phoneshop.web;

import com.es.phoneshop.core.dao.order.ArrayListOrderDao;
import com.es.phoneshop.core.dao.order.DeliveryMode;
import com.es.phoneshop.core.dao.order.Order;
import com.es.phoneshop.core.dao.order.OrderDao;
import com.es.phoneshop.core.order.OrderService;
import com.es.phoneshop.core.order.OrderServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class CheckoutPageServlet extends HttpServlet {
    protected static final String DELIVERY_MODE = "deliveryMode";

    private OrderService orderService;
    private OrderDao orderDao;

    @Override
    public void init(ServletConfig config) {
        orderDao = ArrayListOrderDao.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(DELIVERY_MODE, orderService.getDeliveryMode(req));
        req.setAttribute("deliveryModes", Arrays.asList(DeliveryMode.values()));
        req.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Order order = orderService.createOrder(req);
        if (orderService.isOrderValid(order)) {
            orderDao.placeOrder(order);
            resp.sendRedirect(req.getContextPath() + "/order/overview/" + order.getId());
        } else {
            req.setAttribute(DELIVERY_MODE, order.getDeliveryMode());
            doGet(req, resp);
        }
    }

    protected void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    protected void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
