package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PriceHistoryPageServlet extends HttpServlet {
    protected ProductDao productDao;
    private static final String PRICE_HISTORY_JSP_PATH = "/WEB-INF/pages/priceHistory.jsp";
    private static final String PRICE_HISTORY = "history";

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo();
        request.setAttribute(PRICE_HISTORY, productDao.getProduct(Long.valueOf(productId.substring(1))).getHistories());
        request.getRequestDispatcher(PRICE_HISTORY_JSP_PATH).forward(request, response);
    }
}
