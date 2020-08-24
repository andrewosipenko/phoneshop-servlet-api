package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.entity.Cart;
import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.HttpServletCartService;
import com.es.phoneshop.model.order.entity.Order;
import com.es.phoneshop.model.order.entity.PaymentMethod;
import com.es.phoneshop.model.order.service.OrderService;
import com.es.phoneshop.model.order.service.OrderServiceImpl;
import com.es.phoneshop.web.constants.CheckoutParamKeys;
import com.es.phoneshop.web.constants.ControllerConstants;

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
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CheckoutPageServlet extends HttpServlet {
    private static final String ORDER_ATTRIBUTE_NAME = "order";
    private static final String PAYMENT_METHODS_ATTRIBUTE_NAME = "paymentMethods";

    private CartService<HttpServletRequest> cartService;
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = HttpServletCartService.INSTANCE;
        orderService = OrderServiceImpl.INSTANCE;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ORDER_ATTRIBUTE_NAME, orderService.getOrder(cartService.getCart(request)));
        request.setAttribute(PAYMENT_METHODS_ATTRIBUTE_NAME, orderService.getPaymentMethods());
        request.getRequestDispatcher(ControllerConstants.CHECKOUT_JSP_PATH).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> errors = new HashMap<>();
        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        setRequiredStringParameter(request, String.valueOf(CheckoutParamKeys.firstName), errors, order::setFirstName);
        setRequiredStringParameter(request, String.valueOf(CheckoutParamKeys.lastName), errors, order::setLastName);
        setRequiredStringParameter(request, String.valueOf(CheckoutParamKeys.phone), errors, order::setPhone,
                this::BYphoneNumberValidationPredicate, ControllerConstants.WRONG_PHONE_FORMAT_ERROR_MESSAGE);
        setRequiredDeliveryDate(request, errors, order);
        setRequiredStringParameter(request, String.valueOf(CheckoutParamKeys.deliveryAddress), errors, order::setDeliveryAddress);
        setRequiredPaymentMethod(request, errors, order);

        if(errors.isEmpty()){
            orderService.placeOrder(order);
            cartService.clearCart(cart);
            response.sendRedirect(request.getContextPath()
                    + ControllerConstants.ORDER_OVERVIEW_PAGE_PATH
                    + order.getSecureId());
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute(ORDER_ATTRIBUTE_NAME, orderService.getOrder(cartService.getCart(request)));
            request.setAttribute(PAYMENT_METHODS_ATTRIBUTE_NAME, orderService.getPaymentMethods());
            request.getRequestDispatcher(ControllerConstants.CHECKOUT_JSP_PATH).forward(request, response);
        }
    }

    boolean BYphoneNumberValidationPredicate(String phoneNumber) {
        return phoneNumber.matches("^[+]375[(](17|29|33|44)[)]*[\\s\\./0-9]{7}$");
    }

    private void setRequiredStringParameter(HttpServletRequest request, String parameter, Map<String, String> errors,
                                            Consumer<String> consumer) {
        String requiredValue = request.getParameter(parameter);
        if(requiredValue == null || requiredValue.isEmpty()) {
            errors.put(parameter, ControllerConstants.REQUIRED_VALUE_ERROR_MESSAGE);
        } else {
            consumer.accept(requiredValue);
        }
    }

    //don't know how to avoid 6 parameter to make generic method
    private void setRequiredStringParameter(HttpServletRequest request, String parameter, Map<String, String> errors,
                                            Consumer<String> consumer, Predicate<String> predicate, String errorMessage) {
        String requiredValue = request.getParameter(parameter);
        if(requiredValue == null || requiredValue.isEmpty() || !predicate.test(requiredValue)) {
            errors.put(parameter, errorMessage);
        } else {
            consumer.accept(requiredValue);
        }
    }

    private void setRequiredPaymentMethod(HttpServletRequest request, Map<String, String> errors, Order order) {
        String requiredValue = request.getParameter(String.valueOf(CheckoutParamKeys.paymentMethod));
        if(requiredValue == null || requiredValue.isEmpty()) {
            errors.put(String.valueOf(CheckoutParamKeys.paymentMethod), ControllerConstants.REQUIRED_VALUE_ERROR_MESSAGE);
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(requiredValue));
        }
    }

    private void setRequiredDeliveryDate(HttpServletRequest request, Map<String, String> errors, Order order) {
        String rawRequiredDate = request.getParameter(String.valueOf(CheckoutParamKeys.deliveryDate));
        String[] requiredDate = rawRequiredDate.split("-");
        try {
            int year = Integer.parseInt(requiredDate[0]);
            int month = Integer.parseInt(requiredDate[1]);
            int day = Integer.parseInt(requiredDate[2]);
            LocalDate parsedDate = LocalDate.of(year, month, day);
            if(parsedDate.compareTo(LocalDate.now()) >= 0){
                order.setDeliveryDate(LocalDate.of(year, month, day));
            } else {
                errors.put(String.valueOf(CheckoutParamKeys.deliveryDate), ControllerConstants. PAST_DATE_ERROR_MESSAGE);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | DateTimeException e) {
            errors.put(String.valueOf(CheckoutParamKeys.deliveryDate), ControllerConstants.WRONG_DATE_INPUT_ERROR_MESSAGE);
        }
    }
}
