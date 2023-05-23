package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;

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
    private static final String ERROR = "error";
    private static final String QUANTITY = "quantity";
    private static final String SUCCESSFULLY_ADD_MESSAGE = "Added to card successfully";

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
        Map<Long, String> error = new HashMap<>();
        Long productId = getProductIdFromUrl(request);
        try {
            int quantity = validateQuantityInput(request, request.getParameter(QUANTITY));
            Cart cart = cartService.getCart(request);
            cartService.add(productId, quantity, cart);
        } catch (ParseException | NumberFormatException | OutOfStockException e) {
            error.put(productId, e.getMessage());
        }
        if(error.isEmpty()) {
            response.sendRedirect(String.format("%s/products/addCartItem/%d?message=%s",
                    request.getContextPath(), productId, SUCCESSFULLY_ADD_MESSAGE));
        } else {
            request.setAttribute(ERROR, error);
            doGet(request, response);
        }
    }
}
