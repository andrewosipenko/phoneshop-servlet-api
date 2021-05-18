package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.HttpServletCartService;
import com.es.phoneshop.model.order.entity.Order;
import com.es.phoneshop.model.order.entity.PaymentMethod;
import com.es.phoneshop.model.order.service.OrderService;
import com.es.phoneshop.model.order.service.OrderServiceImpl;
import com.es.phoneshop.web.constants.CheckoutParamKeys;
import com.es.phoneshop.web.constants.ControllerConstants;
import com.es.phoneshop.web.constants.PostProductParamKeys;
import com.es.phoneshop.web.utils.UrlParamsSection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class OrderOverviewPageServlet extends HttpServlet {
    private static final String ORDER_ATTRIBUTE_NAME = "order";

    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = OrderServiceImpl.INSTANCE;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secureOrderId = request.getPathInfo().substring(1);
        request.setAttribute(ORDER_ATTRIBUTE_NAME, orderService.getOrderBySecureId(secureOrderId));
        request.getRequestDispatcher(ControllerConstants.ORDER_OVERVIEW_JSP_PATH).forward(request, response);
    }
}
