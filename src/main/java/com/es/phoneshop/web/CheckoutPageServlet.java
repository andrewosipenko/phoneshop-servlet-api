package com.es.phoneshop.web;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Customer;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.OrderService;
import com.es.phoneshop.services.impl.CartServiceImpl;
import com.es.phoneshop.services.impl.OrderServiceImpl;
import com.es.phoneshop.utils.validator.SimpleValidator;
import com.es.phoneshop.utils.validator.SimpleValidatorImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutPageServlet extends HttpServlet {
    private final int UNPROCESSABLE_ENTITY = 422;
    private CartService cartService;
    private OrderService orderService;
    private SimpleValidator simpleValidator;

    @Override
    public void init() throws ServletException {
        cartService = CartServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
        simpleValidator = SimpleValidatorImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCart(req);

        if (cart.getCartItems().size() == 0) {
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else {
            req.setAttribute("cart", cart);
            req.setAttribute("paymentMethod", "money");
            BigDecimal deliveryCost = orderService.getDeliveryCost();
            req.setAttribute("deliveryCost", deliveryCost);
            req.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(req, resp);
        }
    }

    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCart(req);
        Map<String, List<String>> errors = new HashMap<>();
        checkAllFieldOnValid(req, errors);
        if (errors.isEmpty()) {
            try {
                cartService.updateWithoutChangesProducts(cart);
            } catch (Exception e) {
                resp.sendError(UNPROCESSABLE_ENTITY, e.getMessage() + " make update your cart.");
            }
            Customer customer = saveCustomer(req);
            HashMap<String, String> additionalInformation = getAdditionalInformation(req);
            Order order = orderService.generateOrder(cart, customer, additionalInformation);
            orderService.placeOrder(order);
            cartService.clearCart(cart);
            resp.sendRedirect(req.getContextPath() + "/order/overview/" + order.getSecureId());
        } else {
            req.setAttribute("errors", errors);
            doGet(req, resp);
        }
    }

    private HashMap<String, String> getAdditionalInformation(HttpServletRequest request) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("deliveryDate", request.getParameter("deliveryDate"));
        hashMap.put("deliveryAddress", request.getParameter("deliveryAddress"));
        hashMap.put("paymentMethod", request.getParameter("paymentMethod"));
        return hashMap;
    }

    private void checkAllFieldOnValid(HttpServletRequest request, Map<String, List<String>> errors) {
        simpleValidator.newErrorList().setCheckedValue(request.getParameter("firstName"))
                .lengthLessThen(15).lengthMoreThen(2).addToMapErrorsIfExist(errors, "firstName");
        simpleValidator.newErrorList().setCheckedValue(request.getParameter("secondName"))
                .lengthMoreThen(2).lengthLessThen(15).addToMapErrorsIfExist(errors, "secondName");
        simpleValidator.newErrorList().setCheckedValue(request.getParameter("phoneNumber"))
                .isPhoneNumber().addToMapErrorsIfExist(errors, "phoneNumber");
        simpleValidator.newErrorList().setCheckedValue(request.getParameter("deliveryDate"))
                .isDateByFormat("DD.MM.yyyy").addToMapErrorsIfExist(errors, "deliveryDate");
        simpleValidator.newErrorList().setCheckedValue(request.getParameter("deliveryAddress"))
                .lengthMoreThen(3).addToMapErrorsIfExist(errors, "deliveryAddress");
        simpleValidator.newErrorList().setCheckedValue(request.getParameter("paymentMethod"))
                .notEmpty().addToMapErrorsIfExist(errors, "paymentMethod");
    }

    private Customer saveCustomer(HttpServletRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getParameter("firstName"));
        customer.setLastName(request.getParameter("secondName"));
        customer.setPhoneNumber(request.getParameter("phoneNumber"));
        return customer;
    }
}
