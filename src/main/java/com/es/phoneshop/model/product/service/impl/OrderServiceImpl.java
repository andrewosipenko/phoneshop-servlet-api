package com.es.phoneshop.model.product.service.impl;

import com.es.phoneshop.model.product.dao.OrderDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.model.product.entity.Cart;
import com.es.phoneshop.model.product.entity.CartItem;
import com.es.phoneshop.model.product.entity.Order;
import com.es.phoneshop.model.product.enums.PaymentMethod;
import com.es.phoneshop.model.product.service.CartService;
import com.es.phoneshop.model.product.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static volatile OrderServiceImpl instance;

    private final String POSSIBLE_ERROR_MESSAGE_NO_FILLING = "This field must be filled in!";
    private final String POSSIBLE_ERROR_MESSAGE_HAS_ERRORS_PHONE = "There were some errors in phone number!\nFormat: +(375/80)(29/44/25/33)(xxxxxxx)";
    private final String POSSIBLE_ERROR_MESSAGE_HAS_ERRORS_DATE = "There were some errors in date!\nFormat: dd.mm.yyyy";
    private final String PHONE_NUMBER_REQUEST_PARAMETER = "phoneNumber";

    private final String DELIVERY_DATE_REQUEST_PARAMETER = "deliveryDate";
    private final String PAYMENT_METHOD_REQUEST_PARAMETER = "paymentMethod";
    private final String PHONE_VALIDATION_REG_EXP = "^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$";


    private OrderDao orderDao;
    private CartService cartService;

    public static OrderServiceImpl getInstance() {
        OrderServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (OrderServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new OrderServiceImpl();
            }
        }
        return localInstance;
    }

    private OrderServiceImpl() {
        orderDao = ArrayListOrderDao.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    public Order createOrderFromCart(Cart example) {
        Order order = new Order();
        order.setItems(example.getItems().stream().
                map(cartItem -> (CartItem) cartItem.clone()).
                collect(Collectors.toList())
        );
        order.setSubTotalCost(example.getTotalCost());
        order.setDeliveryCost(calculateDeliveryCost());
        order.setTotalCost(order.getSubTotalCost().add(order.getDeliveryCost()));
        order.setTotalItems(example.getTotalItems());

        return order;
    }

    private BigDecimal calculateDeliveryCost() {
        return BigDecimal.TEN;
    }

    @Override
    public void setRequiredStringCustomerInfo(Order order,
                                              String requiredParameter,
                                              HttpServletRequest request,
                                              Map<String, String> errorsMap,
                                              Consumer<String> consumer) {
        String parameter = request.getParameter(requiredParameter);

        if (parameter == null || parameter.isEmpty()) {
            errorsMap.put(requiredParameter, POSSIBLE_ERROR_MESSAGE_NO_FILLING);
        } else {
            consumer.accept(parameter);
        }
    }

    @Override
    public void setRequiredPhoneNumberCustomerInfo(Order order,
                                                   HttpServletRequest request,
                                                   Map<String, String> errorsMap) {
        String phoneNumber = request.getParameter(PHONE_NUMBER_REQUEST_PARAMETER);

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            errorsMap.put(PHONE_NUMBER_REQUEST_PARAMETER, POSSIBLE_ERROR_MESSAGE_NO_FILLING);
        } else {
            if (!phoneNumber.matches(PHONE_VALIDATION_REG_EXP)) {
                errorsMap.put(PHONE_NUMBER_REQUEST_PARAMETER, POSSIBLE_ERROR_MESSAGE_HAS_ERRORS_PHONE);
            } else {
                order.getCustomerInfo().setPhoneNumber(phoneNumber);
            }
        }
    }

    @Override
    public void setRequiredLocalDateCustomerInfo(Order order,
                                                 HttpServletRequest request,
                                                 Map<String, String> errorsMap) {

        String parameter = request.getParameter(DELIVERY_DATE_REQUEST_PARAMETER);

        if (parameter == null || parameter.isEmpty()) {
            errorsMap.put(DELIVERY_DATE_REQUEST_PARAMETER, POSSIBLE_ERROR_MESSAGE_NO_FILLING);
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.getDefault());
                LocalDate localDateParameter = LocalDate.parse(parameter, formatter);
                order.getCustomerInfo().setDeliveryDate(localDateParameter);
            } catch (DateTimeParseException e) {
                errorsMap.put(DELIVERY_DATE_REQUEST_PARAMETER, POSSIBLE_ERROR_MESSAGE_HAS_ERRORS_DATE);
            }
        }
    }

    @Override
    public void setRequiredPaymentMethodCustomerInfo(Order order,
                                                     HttpServletRequest request,
                                                     Map<String, String> errorsMap) {
        String parameter = request.getParameter(PAYMENT_METHOD_REQUEST_PARAMETER);

        if (parameter == null || parameter.isEmpty()) {
            errorsMap.put(PAYMENT_METHOD_REQUEST_PARAMETER, POSSIBLE_ERROR_MESSAGE_NO_FILLING);
        } else {
            order.getCustomerInfo().setPaymentMethod(PaymentMethod.valueOf(parameter));
        }
    }

    @Override
    public void placeOrder(Order orderToPlace, HttpServletRequest request) {
        orderToPlace.setSecureId(String.valueOf(UUID.randomUUID()));
        orderDao.save(orderToPlace);

        Cart currentCart = cartService.getCart(request);
        currentCart.getItems().clear();
        cartService.reCalculateCart(currentCart);
    }

    @Override
    public String parseSecureOrderIdFromRequest(HttpServletRequest request) {
        return request.getPathInfo().substring(1);
    }

}
