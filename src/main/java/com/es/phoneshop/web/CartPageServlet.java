package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CartPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean isSuccessful = updateCart(request);
        if (isSuccessful) {
            response.sendRedirect(request.getRequestURI() + "?success=true");
        } else {
            doGet(request, response);
        }
    }

    private boolean updateCart(HttpServletRequest request) {
        String[] productsIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        String[] errors = new String[productsIds.length];
        Cart cart = cartService.getCart(request.getSession());
        for (int i = 0; i < productsIds.length; i++) {
            try {
                cartService.update(request.getSession(),cart, productsIds, quantities, request.getLocale());
            } catch (NumberFormatException e) {
                errors[i] = "Quantity should be a number";
            } catch (IllegalQuantityException | LackOfStockException e) {
                errors[i] = e.getMessage();
            }
        }
        boolean isSuccessful = Arrays.stream(errors).noneMatch(Objects::nonNull);
        if (isSuccessful) {
            cartService.calculateTotalPrice(cart);
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("quantities", quantities);
            request.setAttribute("success", isSuccessful);
        }
        return isSuccessful;
    }
}