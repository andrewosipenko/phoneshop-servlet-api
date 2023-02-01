package com.es.phoneshop.web.pages;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;

    private Map<Long, String> errors;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request).getCartItems());
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> quantities = new ArrayList<>();
        List<Long> productIds = new ArrayList<>();
        errors = new HashMap<>();
        String[] productId = request.getParameterValues("productId");
        String[] quantity = request.getParameterValues("quantity");

        if (quantity.length != productId.length) {
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
            return;
        }
        if (productId.length == 0) {
            doGet(request, response);
            return;
        }
        parse(request, productIds, quantities);

        for (int i = 0; i < quantities.size(); i++) {
            try {
                verifyQuantity(quantities.get(i));
                cartService.update(request, productIds.get(i), quantities.get(i));
            } catch (IllegalArgumentException e) {
                errors.put(productIds.get(i), "Negative number or zero");
            } catch (OutOfStockException e) {
                errors.put(productIds.get(i), "Out of stock");
            }
        }

        response.sendRedirect(request.getContextPath() + "/cart?message=Cart successfully updated");
    }

    private void verifyQuantity(int quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private void parse(HttpServletRequest request, List<Long> productIds, List<Integer> quantities) {
        String[] productIdsString = request.getParameterValues("productId");
        String[] quantitiesString = request.getParameterValues("quantity");
        for (int i = 0; i < quantitiesString.length; i++) {
            try {
                productIds.add(Long.parseLong(productIdsString[i]));
                quantities.add(Integer.parseInt(quantitiesString[i]));
            } catch (NumberFormatException e) {
                errors.put(Long.parseLong(productIdsString[i]), "Not a number");
            }
        }
    }
}
