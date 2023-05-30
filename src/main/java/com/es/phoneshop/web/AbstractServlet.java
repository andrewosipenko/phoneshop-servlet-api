package com.es.phoneshop.web;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.service.BrowsingHistoryService;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import com.es.phoneshop.service.impl.BrowsingHistoryServiceImpl;
import com.es.phoneshop.service.impl.CartServiceImpl;
import com.es.phoneshop.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

public class AbstractServlet extends HttpServlet {
    protected CartService cartService;
    protected OrderService orderService;
    protected ProductDao productDao;
    protected OrderDao orderDao;
    protected BrowsingHistoryService browsingHistoryService;
    private static final String ERROR_MESSAGE = "Not a number";

    @Override
    public void init() {
        cartService = CartServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
        productDao = ArrayListProductDao.getInstance();
        orderDao = ArrayListOrderDao.getInstance();
        browsingHistoryService = BrowsingHistoryServiceImpl.getInstance();
    }

    protected int validateQuantityInput(HttpServletRequest request, String quantityValue) throws ParseException {
        if (!quantityValue.matches("^([1-9]\\,?0*)?|([1-9]\\d+\\,?0*)?$")) {
            throw new NumberFormatException(ERROR_MESSAGE);
        }
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        return format.parse(quantityValue).intValue();
    }

    protected Long getItemIdFromUrl(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo().substring(1));
    }

    protected void handleErrors(HttpServletRequest request, HttpServletResponse response, Map<Long, String> errors,
                                    String page, String message) throws IOException, ServletException {
        if(errors.isEmpty()) {
            response.sendRedirect(String.format("%s/%s?message=%s", request.getContextPath(), page, message));
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }
}
