package com.es.phoneshop.web;

import com.es.phoneshop.model.product.exception.OutOfStockException;
import com.es.phoneshop.model.product.service.CartService;
import com.es.phoneshop.model.product.service.ProductService;
import com.es.phoneshop.model.product.service.impl.CartServiceImpl;
import com.es.phoneshop.model.product.service.impl.ProductServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        Map<Long, String> inputErrors = new HashMap<>();

        for (int i = 0; i < productIds.length; i++) {
            try {
                cartService.update(
                        cartService.getCart(request),
                        Long.parseLong(productIds[i]),
                        productService.parseQuantity(quantities[i], request),
                        request);
            } catch (OutOfStockException e) {
                inputErrors.put(
                        Long.parseLong(productIds[i]),
                        "Not enough items in stock! Available:" + e.getAvailableStock());
            } catch (NumberFormatException | ParseException e1) {
                inputErrors.put(
                        Long.parseLong(productIds[i]),
                        "Not a number!");
            }
        }
        if (!inputErrors.isEmpty()) {
            request.setAttribute("inputErrors", inputErrors);
            doGet(request, response);
        } else {
            response.sendRedirect(String.format("%s/cart?message=Cart was successfully updated!", request.getContextPath()));
        }
    }
}
