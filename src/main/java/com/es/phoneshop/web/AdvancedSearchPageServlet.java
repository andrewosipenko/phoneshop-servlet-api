package com.es.phoneshop.web;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.enums.SearchType;
import com.es.phoneshop.enums.SortingField;
import com.es.phoneshop.enums.SortingType;
import com.es.phoneshop.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

public class AdvancedSearchPageServlet extends AbstractServlet {
    private static final String ADVANCED_SEARCH_JSP_PATH = "/WEB-INF/pages/advancedSearch.jsp";
    private static final String DESCRIPTION = "description";
    private static final String MIN_PRICE = "minPrice";
    private static final String MAX_PRICE = "maxPrice";
    private static final String SEARCH_TYPE = "searchType";
    private static final String SEARCH_TYPES = "searchTypes";
    private static final String PRODUCTS = "products";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(SEARCH_TYPES, Arrays.asList(SearchType.values()));
        String description = request.getParameter(DESCRIPTION);
        String minPrice = request.getParameter(MIN_PRICE);
        String maxPrice = request.getParameter(MAX_PRICE);
        String searchingType = request.getParameter(MAX_PRICE);
        request.setAttribute(PRODUCTS, productDao.advancedFindProducts(description,
                minPrice != null ? BigDecimal.valueOf(Long.parseLong(minPrice)) : null,
                maxPrice != null ? BigDecimal.valueOf(Long.parseLong(maxPrice)) : null,
                searchingType != null ? SearchType.valueOf(searchingType) : null));
        request.getRequestDispatcher(ADVANCED_SEARCH_JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
