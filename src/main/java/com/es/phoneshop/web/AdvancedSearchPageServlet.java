package com.es.phoneshop.web;

import com.es.phoneshop.service.AdvancedSearchService;
import com.es.phoneshop.service.impl.DefaultAdvancedSearchService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AdvancedSearchPageServlet extends HttpServlet {
    private AdvancedSearchService searchService;

    @Override
    public void init(ServletConfig config) {
        searchService = DefaultAdvancedSearchService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", new ArrayList());
        request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String minStock = request.getParameter("minStock");
        String maxStock = request.getParameter("maxStock");

        request.setAttribute("products", searchService.advancedSearch(description, minPrice, maxPrice, minStock, maxStock));
        request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
    }
}
