package com.es.phoneshop.web;

import com.es.phoneshop.enums.SortingField;
import com.es.phoneshop.enums.SortingType;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

public class ProductListPageServlet extends AbstractServlet {
    private static final String PRODUCT_LIST_JSP_PATH = "/WEB-INF/pages/productList.jsp";
    private static final String DESCRIPTION = "description";
    private static final String SORTING = "sorting";
    private static final String SORTING_TYPE = "type";
    private static final String PRODUCTS = "products";
    private static final String PRODUCTS_RECENTLY_VIEWED = "productsRecentlyViewed";
    private static final String QUANTITY = "quantity";
    private static final String SUCCESSFULLY_ADD_MESSAGE = "Added to card successfully";
    private static final String URL = "products/addCartItem/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter(DESCRIPTION);
        String sortingField = request.getParameter(SORTING);
        String sortingType = request.getParameter(SORTING_TYPE);
        request.setAttribute(PRODUCTS, productDao.findProducts(description,
                Optional.ofNullable(sortingField).map(SortingField::valueOf).orElse(null),
                Optional.ofNullable(sortingType).map(SortingType::valueOf).orElse(null)));
        LinkedList<Product> productList = browsingHistoryService.getBrowsingHistory(request).getProducts();
        request.setAttribute(PRODUCTS_RECENTLY_VIEWED, productList);
        request.getRequestDispatcher(PRODUCT_LIST_JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Long, String> errors = new HashMap<>();
        Long productId = getItemIdFromUrl(request);
        try {
            int quantity = validateQuantityInput(request, request.getParameter(QUANTITY));
            Cart cart = cartService.getCart(request);
            cartService.add(productId, quantity, cart);
        } catch (ParseException | NumberFormatException | OutOfStockException e) {
            errors.put(productId, e.getMessage());
        }
        handleErrors(request, response, errors, URL + productId, SUCCESSFULLY_ADD_MESSAGE);
    }
}
