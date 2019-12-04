package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.DefaultOrderService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.PaymentMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    private static final String WEB_INF_PAGES_CHECKOUT_JSP = "/WEB-INF/pages/checkout.jsp";

    private static final String PHONE_FORMAT = "\\d{7,12}";

    private static final String ORDER = "order";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE = "phone";
    private static final String DELIVERY_DATE = "deliveryDate";
    private static final String DELIVERY_ADDRESS = "deliveryAddress";
    private static final String PAYMENT_METHOD = "paymentMethod";
    private static final String ERROR_MAP = "errorMap";

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        request.setAttribute(ORDER, order);

        request.getRequestDispatcher(WEB_INF_PAGES_CHECKOUT_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        Map<String, String> errorMap = new HashMap<>();
        Map<String, String> paramMap = new HashMap<>();

        getRequiredParameters(request, paramMap, errorMap);

        Date deliveryDate = validateDeliveryDate(request, DELIVERY_DATE, errorMap);
        PaymentMethod paymentMethod = getPaymentMethod(request, PAYMENT_METHOD);

        if (!errorMap.isEmpty()) {
            request.setAttribute(ERROR_MAP, errorMap);
            request.setAttribute(ORDER, order);
            request.getRequestDispatcher(WEB_INF_PAGES_CHECKOUT_JSP).forward(request, response);
            return;
        }

        setOrderParams(order, paramMap, deliveryDate, paymentMethod);

        String secureId = orderService.placeOrder(order);

        cartService.clear(cart);

        response.sendRedirect(request.getContextPath() + "/orderoverview/" + secureId);
    }

    private void getRequiredParameters(HttpServletRequest request, Map<String, String> paramMap, Map<String, String> errorMap) {
        setParamMapKeys(paramMap);
        for (String paramName : paramMap.keySet()) {
            String param;
            if (paramName.equals(PHONE)) {
                param = getRequiredParameter(request, PHONE, errorMap);
                validatePhone(param, errorMap);
            } else {
                param = getRequiredParameter(request, paramName, errorMap);
            }
            paramMap.put(paramName, param);
        }
    }

    private void setParamMapKeys(Map<String, String> paramMap) {
        paramMap.put(FIRST_NAME, "");
        paramMap.put(LAST_NAME, "");
        paramMap.put(PHONE, "");
        paramMap.put(DELIVERY_ADDRESS, "");
    }

    private void setOrderParams(Order order, Map<String, String> paramMap, Date deliveryDate, PaymentMethod paymentMethod) {
        order.setFirstName(paramMap.get(FIRST_NAME));
        order.setLastName(paramMap.get(LAST_NAME));
        order.setPhone(paramMap.get(PHONE));
        order.setDeliveryDate(deliveryDate);
        order.setDeliveryAddress(paramMap.get(DELIVERY_ADDRESS));
        order.setPaymentMethod(paymentMethod);
    }


    private String getRequiredParameter(HttpServletRequest request, String name, Map<String, String> errorMap) {
        String result = request.getParameter(name);
        if (result == null || result.isEmpty()) {
            errorMap.put(name, name + " is required");
        }
        return result;
    }

    private PaymentMethod getPaymentMethod(HttpServletRequest request, String paymentMethod) {
        String stringPaymentMethod = request.getParameter(paymentMethod);
        return stringPaymentMethod == null ? null : PaymentMethod.valueOf(stringPaymentMethod.toUpperCase());
    }

    private void validatePhone(String phone, Map<String, String> errorMap) {
        boolean result = phone.matches(PHONE_FORMAT);
        if (!result) {
            errorMap.put(PHONE, "invalid phone number");
        }
    }

    private Date validateDeliveryDate(HttpServletRequest request, String date, Map<String, String> errorMap) {
        String stringDeliveryDate = getRequiredParameter(request, date, errorMap);
        Date deliveryDate;
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            deliveryDate = dateFormat.parse(stringDeliveryDate);
        } catch (ParseException e) {
            errorMap.put(DELIVERY_DATE, "invalid delivery date");
            return null;
        }
        return deliveryDate;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
