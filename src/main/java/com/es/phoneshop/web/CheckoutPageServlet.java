package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.enums.payment.PaymentMethod;
import com.es.phoneshop.model.product.exceptions.DateBeforeException;
import com.es.phoneshop.model.product.order.DefaultOrderService;
import com.es.phoneshop.model.product.order.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class CheckoutPageServlet extends HttpServlet {

    public static final String CHECKOUT_PAGE_JSP = "/WEB-INF/pages/checkoutPage.jsp";
    private CartService cartService;
    private DefaultOrderService orderService;

    @Override
    public void init() {
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = (Order) request.getAttribute("order");
        if (order == null) {
            request.setAttribute("order", orderService.getOrder(cart));
        }
        request.setAttribute("paymentMethods", orderService.getPaymentMethod());
        request.setAttribute("cart", cart);
        request.getRequestDispatcher(CHECKOUT_PAGE_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        Map<String, String> errorsMap = new HashMap<>();
        setRequiredField(request, "firstName", errorsMap, order::setFirstName);
        setRequiredField(request, "lastName", errorsMap, order::setLastName);
        setRequiredField(request, "deliveryAddress", errorsMap, order::setDeliveryAddress);
        setDeliveryDateField(request, errorsMap, order);
        setPhoneNumber(request, errorsMap, order);
        setPaymentMethod(request, errorsMap, order);

        if (errorsMap.isEmpty()) {
            orderService.placeOrder(order);
            cartService.clear(cart);
            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getSecureId() + "?successMessage=Order successfully placed");
        } else {
            request.setAttribute("order", order);
            request.setAttribute("errorsMap", errorsMap);
            doGet(request, response);
        }
    }

    private void setRequiredField(HttpServletRequest request, String parameterName,
                                  Map<String, String> errors, Consumer<String> consumer) {
        String value = request.getParameter(parameterName);
        if (value == null || value.isEmpty()) {
            errors.put(parameterName, "Field is required");
        } else {
            consumer.accept(value);
        }
    }

    private void setPhoneNumber(HttpServletRequest request, Map<String, String> errors, Order order) {
        Pattern phonePattern =
                Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
        String value = request.getParameter("phone");
        if (value == null || value.isEmpty()) {
            errors.put("phone", "Field is required");
        } else if (!phonePattern.matcher(value).matches()) {
            errors.put("phone", "Error. Phone pattern is +###-##-#######");
        } else {
            order.setPhone(value);
        }
    }

    private void setPaymentMethod(HttpServletRequest request, Map<String, String> errors, Order order) {
        String paymentMethodString = request.getParameter("paymentMethod");
        if (paymentMethodString == null || paymentMethodString.isEmpty()) {
            errors.put("paymentMethod", "Field is required");
        } else {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodString);
            order.setPaymentMethod(paymentMethod);
        }
    }

    private void setDeliveryDateField(HttpServletRequest request, Map<String, String> errors, Order order) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String deliveryDateString = request.getParameter("deliveryDate");
        try {
            Date deliveryDate = simpleDateFormat.parse(deliveryDateString);
            if (deliveryDate.before(new Date())) {
                throw new DateBeforeException("Delivery date should be after current date");
            }
            order.setDeliveryDate(deliveryDateString);
        } catch (ParseException | NullPointerException exception) {
            errors.put("deliveryDate", "Format error");
        } catch (DateBeforeException exception) {
            errors.put("deliveryDate", exception.getMessage());
        }
    }
}