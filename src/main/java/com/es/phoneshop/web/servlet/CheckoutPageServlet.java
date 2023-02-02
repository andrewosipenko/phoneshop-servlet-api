package com.es.phoneshop.web.servlet;

import com.es.phoneshop.enumeration.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.DefaultOrderService;
import com.es.phoneshop.service.HttpSessionCartService;
import com.es.phoneshop.service.OrderService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = HttpSessionCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        request.setAttribute("order", order);
        request.setAttribute("paymentMethods", orderService.getPaymentMethods());

        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        Map<String, String> errors = new HashMap<>();

        setRequiredStringParameter(request, "firstName", errors, order::setFirstName);
        setRequiredStringParameter(request, "lastName", errors, order::setLastName);
        setRequiredStringParameter(request, "deliveryAddress", errors, order::setDeliveryAddress);

        setPhoneParameter(request, errors, order::setPhone);
        setDeliveryDateParameter(request, errors, order::setDeliveryDate);
        setPaymentMethodParameter(request, errors, order::setPaymentMethod);

        request.setAttribute("errors", errors);

        if (errors.isEmpty()) {
            orderService.placeOrder(order, request);
            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getSecureId());
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("order", orderService.getOrder(cart));
            request.setAttribute("paymentMethods", orderService.getPaymentMethods());
            request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
        }
    }

    private void setRequiredStringParameter(HttpServletRequest request, String parameter, Map<String, String> errors, Consumer<String> consumer) {
        String value = request.getParameter(parameter);

        if (value == null || value.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            consumer.accept(value);
        }
    }

    /* temporary solution only validates Belarusian phones */
    private void setPhoneParameter(HttpServletRequest request, Map<String, String> errors, Consumer<String> consumer) {
        String parameter = "phone";
        String phone = request.getParameter(parameter);

        if (phone == null || phone.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            String regexToValidateBelarusianPhones = "(\\+375|80)(29|44|33|25)(\\d{7})";

            if (phone.matches(regexToValidateBelarusianPhones)) {
                consumer.accept(phone);
            } else {
                errors.put(parameter, "Incorrect phone");
            }
        }
    }

    private void setDeliveryDateParameter(HttpServletRequest request, Map<String, String> errors, Consumer<LocalDate> consumer) {
        String parameter = "deliveryDate";
        String dateString = request.getParameter(parameter);

        if (dateString == null || dateString.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(dateString);
                if (date.getTime() <= new Date().getTime()) {
                    errors.put(parameter, "Delivery date should be today or further");
                    return;
                }
                consumer.accept(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                errors.put(parameter, "Incorrect date");
            }
        }
    }

    private void setPaymentMethodParameter(HttpServletRequest request, Map<String, String> errors, Consumer<PaymentMethod> consumer) {
        String parameter = "paymentMethod";
        String paymentMethodString = request.getParameter(parameter);

        if (paymentMethodString == null || paymentMethodString.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodString);
            consumer.accept(paymentMethod);
        }
    }
}
