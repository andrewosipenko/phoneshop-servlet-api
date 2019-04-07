package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
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
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) {
        cartService = HttpSessionCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getAttribute("errors"));
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
                if (productDao.getProduct(id).getStock() < quantity) {
                    errors[i] = Error.OUT_OF_STOCK.getErrorMessage();
                    continue;
                }
                cartService.update(cart, id, quantity);
            } catch (NumberFormatException e) {
                errors[i] = Error.PARSE_ERROR.getErrorMessage();
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
