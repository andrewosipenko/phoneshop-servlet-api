package com.es.phoneshop.web.servlet;

import com.es.phoneshop.util.CartItemQuantityValidationUtil;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.HttpSessionCartService;
import com.es.phoneshop.exception.OutOfStockException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {

    private static final long serialVersionUID = 8174769907867467091L;

    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        Map<Long, String> errors = new HashMap<>();
        for (int i = 0; i < productIds.length; i++) {
            Long productId = Long.parseLong(productIds[i]);
            String quantityString = quantities[i];

            String errorMessage = CartItemQuantityValidationUtil.validateQuantity(request, quantityString);

            if(errorMessage != null) {
                errors.put(productId, errorMessage);
                break;
            }

            int quantity = CartItemQuantityValidationUtil.parseQuantity(request, quantityString).intValue();
            try {
                cartService.update(productId, quantity, request);
            } catch (OutOfStockException e) {
                errors.put(productId, "Out of stock, available " + e.getStock());
            }
        }

        if (errors.isEmpty()) {
            request.setAttribute("message", "Cart is updated");
            response.sendRedirect(request.getContextPath() + "/cart?message=Cart is updated");
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }
}
