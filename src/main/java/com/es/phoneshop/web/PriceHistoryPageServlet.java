package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PriceHistoryPageServlet extends HttpServlet {
    private static final String PRODUCT = "product";
    private static final String PRODUCT_ID = "productId";
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long productId = Long.valueOf(request.getParameter(PRODUCT_ID));
        request.setAttribute(PRODUCT, productDao.getProduct(productId));
        request.getRequestDispatcher("/WEB-INF/pages/priceHistory.jsp").forward(request, response);
    }
}
