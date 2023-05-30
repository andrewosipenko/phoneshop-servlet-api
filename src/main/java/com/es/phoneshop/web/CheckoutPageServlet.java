package com.es.phoneshop.web;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends AbstractServlet {
    private static final String CHECKOUT_JSP_PATH = "/WEB-INF/pages/checkout.jsp";
    private static final String ORDER = "order";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE = "phone";
    private static final String DELIVERY_ADDRESS = "deliveryAddress";
    private static final String DELIVERY_DATE = "deliveryDate";
    private static final String PAYMENT_METHOD = "paymentMethod";
    private static final String ERRORS = "errors";
    private static final String REQUIRED_FIELD_ERROR_MESSAGE = "The field is required";
    private static final String PHONE_ERROR_MESSAGE = "Phone must match the template \"+375(##)###-##-##\"";
    private static final String NAME_ERROR_MESSAGE = "The field must contain only letters of English alphabet";
    private static final String DATE_BEFORE_CURRENT_MESSAGE = "Date must be after the current date";
    private static final String INVALID_DATE_MESSAGE = "Invalid date";
    private static final String PAYMENT_METHODS = "paymentMethods";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String PHONE_REGEX = "^\\+[0-9]{12}$";
    private static final String NAME_REGEX = "^[a-zA-Z]+$";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute(ORDER, orderService.getOrder(cart));
        request.setAttribute(PAYMENT_METHODS, orderService.getPaymentMethods());
        request.getRequestDispatcher(CHECKOUT_JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);
        Map<String, String> errors = new HashMap<>();
        setParameters(request, errors, order);
        handleErrors(request, response, errors, order, cart);
    }

    private void setParameters(HttpServletRequest request, Map<String, String> errors, Order order) {
        setRequiredParameter(request, FIRST_NAME, errors, order::setFirstName);
        setRequiredParameter(request, LAST_NAME, errors, order::setLastName);
        setRequiredParameter(request, PHONE, errors, order::setPhone);
        setRequiredParameter(request, DELIVERY_ADDRESS, errors, order::setDeliveryAddress);
        setName(request, FIRST_NAME, errors, order::setFirstName);
        setName(request, LAST_NAME, errors, order::setLastName);
        setPhone(request, PHONE, errors, order::setPhone);
        setDeliveryDate(request, errors, order);
        setPaymentMethod(request, errors, order);
    }

    private void setRequiredParameter(HttpServletRequest request, String parameter, Map<String, String> errors,
                                      Consumer<String> consumer) {
        String value = request.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            errors.put(parameter, REQUIRED_FIELD_ERROR_MESSAGE);
        } else {
            consumer.accept(value);
        }
    }

    private void setPhone(HttpServletRequest request, String parameter, Map<String, String> errors,
                            Consumer<String> consumer) {
        String phone = request.getParameter(parameter);
        if (!phone.matches(PHONE_REGEX)) {
            errors.put(parameter, PHONE_ERROR_MESSAGE);
        } else {
            consumer.accept(phone);
        }
    }

    private void setName(HttpServletRequest request, String parameter, Map<String, String> errors,
                          Consumer<String> consumer) {
        String name = request.getParameter(parameter);
        if (!name.matches(NAME_REGEX)) {
            errors.put(parameter, NAME_ERROR_MESSAGE);
        } else {
            consumer.accept(name);
        }
    }

    private void setPaymentMethod(HttpServletRequest request, Map<String, String> errors, Order order) {
        String value = request.getParameter(PAYMENT_METHOD);
        if (value.isEmpty()) {
            errors.put(PAYMENT_METHOD, REQUIRED_FIELD_ERROR_MESSAGE);
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(value));
        }
    }

    private void setDeliveryDate(HttpServletRequest request, Map<String, String> errors, Order order) {
        String value = request.getParameter(DELIVERY_DATE);
        if (value == null || value.isEmpty()) {
            errors.put(DELIVERY_DATE, REQUIRED_FIELD_ERROR_MESSAGE);
        } else if (!isDateValid(value)) {
            errors.put(DELIVERY_DATE, INVALID_DATE_MESSAGE);
        } else if (!isDateAfterCurrentDate(value)) {
            errors.put(DELIVERY_DATE, DATE_BEFORE_CURRENT_MESSAGE);
        } else {
            order.setDeliveryDate(LocalDate.parse(value, DateTimeFormatter.ofPattern(DATE_FORMAT)));
        }
    }

    private boolean isDateValid(String deliveryDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_FORMAT);
        myFormat.setLenient(false);
        try {
            myFormat.parse(deliveryDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isDateAfterCurrentDate(String deliveryDate) {
        LocalDate date = LocalDate.parse(deliveryDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
        return date.isAfter(LocalDate.now()) ? true : false;
    }

    public void handleErrors(HttpServletRequest request, HttpServletResponse response, Map<String, String> errors,
                             Order order, Cart cart) throws IOException, ServletException {
        if (errors.isEmpty()) {
            orderService.placeOrder(order);
            cartService.clearCart(cart, request);
            response.sendRedirect(String.format("%s/order/overview/%s", request.getContextPath(), order.getSecureId()));
        } else {
            request.setAttribute(ERRORS, errors);
            request.setAttribute(ORDER, order);
            request.setAttribute(PAYMENT_METHODS, orderService.getPaymentMethods());
            request.getRequestDispatcher(CHECKOUT_JSP_PATH).forward(request, response);
        }
    }
}
