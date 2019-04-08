package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.web.helper.Error;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CartPageServlet extends HttpServlet {

    private HttpSessionCartService cartService;

    @Override
    public void init(ServletConfig config) {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("id");
        String[] quantities = req.getParameterValues("quantity");
        String[] errors = new String[ids.length];
        Cart cart = cartService.getCart(req);

        for (int i = 0; i < ids.length; ++i) {
            Long id = Long.valueOf(ids[i]);
            try {
                Integer quantity = Integer.valueOf(quantities[i]);
                if (quantity < 1) {
                    errors[i] = Error.INVALID_NUMBER.getErrorMessage();
                    continue;
                }
                cartService.update(cart, id, quantity);
            } catch (NumberFormatException e) {
                errors[i] = Error.PARSE_ERROR.getErrorMessage();
            } catch (OutOfStockException e) {
                errors[i] = Error.OUT_OF_STOCK.getErrorMessage();
            }
        }

        boolean hasError = Arrays.stream(errors).anyMatch(Objects::nonNull);
        if (hasError) {
            req.setAttribute("errors", errors);
            doGet(req, resp);
        } else {
            cartService.save(req);
            resp.sendRedirect(req.getRequestURI() + "?message=Updated successfully");
        }
    }
}
