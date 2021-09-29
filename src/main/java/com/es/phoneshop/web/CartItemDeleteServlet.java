package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.exceptions.DeleteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {

    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String CART_PAGE_JSP = "/WEB-INF/pages/cartPage.jsp";

    private CartService cartService;
    private WebHelperService webHelperService;

    @Override
    public void init() {
        cartService = DefaultCartService.getInstance();
        webHelperService = WebHelperService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher(CART_PAGE_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productDeleteId = webHelperService.parseProductId(request);
        Cart cart = cartService.getCart(request);
        try {
            cartService.deleteFromCart(cart, productDeleteId);
        } catch (DeleteException exception) {
            setErrorMessage(request, response, "Some problems. There are no product in cart");
        }
        response.sendRedirect(request.getContextPath() + "/cart?successMessage=Product successfully deleted");
    }

    private void setErrorMessage(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        doGet(request, response);
    }
}