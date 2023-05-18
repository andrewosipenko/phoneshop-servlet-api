package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.BrowsingHistory;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.service.BrowsingHistoryService;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.BrowsingHistoryServiceImpl;
import com.es.phoneshop.service.impl.CartServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private BrowsingHistoryService browsingHistoryService;
    private static final String PRODUCT = "product";
    private static final String CART = "cart";
    private static final String QUANTITY = "quantity";
    private static final String ERROR = "error";
    private static final String PRODUCTS = "products";

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
        browsingHistoryService = BrowsingHistoryServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getProductIdFromUrl(request);
        request.setAttribute(PRODUCT, productDao.getProduct(productId));
        request.setAttribute(CART, cartService.getCart(request));
        request.setAttribute(PRODUCTS, browsingHistoryService.getBrowsingHistory(request).getProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        BrowsingHistory browsingHistory = browsingHistoryService.getBrowsingHistory(request);
        browsingHistoryService.add(productId, browsingHistory);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = getProductIdFromUrl(request);
        try {
            int quantity = validateQuantityInput(request);
            Cart cart = cartService.getCart(request);
            cartService.add(productId, quantity, cart);
        } catch (ParseException | NumberFormatException | OutOfStockException e) {
            request.setAttribute(ERROR, e.getMessage());
            doGet(request, response);
            return;
        }
        response.sendRedirect(String.format("%s/products/%d?message=Added to card successfully",
                request.getContextPath(), productId));
    }

    private int validateQuantityInput(HttpServletRequest request) throws ParseException {
        String quantityValue = request.getParameter(QUANTITY);
        if (!quantityValue.matches("^(\\d+\\,?0*)?$")) {
            throw new NumberFormatException("Not a number");
        }
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        return format.parse(quantityValue).intValue();
    }

    private Long getProductIdFromUrl(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
