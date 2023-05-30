package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.BrowsingHistory;
import com.es.phoneshop.model.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public class ProductDetailsPageServlet extends AbstractServlet {
    private static final String PRODUCT_DETAILS_JSP_PATH = "/WEB-INF/pages/productDetails.jsp";
    private static final String PRODUCT = "product";
    private static final String QUANTITY = "quantity";
    private static final String ERROR = "error";
    private static final String PRODUCTS = "products";
    private static final String SUCCESSFULLY_ADD_MESSAGE = "Added to card successfully";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getItemIdFromUrl(request);
        request.setAttribute(PRODUCT, productDao.getProduct(productId));
        request.setAttribute(PRODUCTS, browsingHistoryService.getBrowsingHistory(request).getProducts());
        request.getRequestDispatcher(PRODUCT_DETAILS_JSP_PATH).forward(request, response);
        BrowsingHistory browsingHistory = browsingHistoryService.getBrowsingHistory(request);
        browsingHistoryService.add(productId, browsingHistory);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getItemIdFromUrl(request);
        try {
            int quantity = validateQuantityInput(request, request.getParameter(QUANTITY));
            Cart cart = cartService.getCart(request);
            cartService.add(productId, quantity, cart);
        } catch (ParseException | NumberFormatException | OutOfStockException e) {
            request.setAttribute(ERROR, e.getMessage());
            doGet(request, response);
            return;
        }
        response.sendRedirect(String.format("%s/products/%d?message=%s",
                request.getContextPath(), productId, SUCCESSFULLY_ADD_MESSAGE));
    }
}
