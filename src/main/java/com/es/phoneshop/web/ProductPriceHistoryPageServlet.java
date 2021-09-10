package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ProductPriceHistoryPageServlet extends HttpServlet {

    ProductDao productDao;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo();
        request.setAttribute("product", productDao.getProduct(Long.valueOf(productId.substring(1))));
        if (productDao.getProduct(Long.valueOf(productId.substring(1))).getPriceHistoryList() == null ||
                productDao.getProduct(Long.valueOf(productId.substring(1))).getPriceHistoryList().isEmpty()) {
            request.setAttribute("productHistoryList", new ArrayList<>());
        } else {
            request.setAttribute("productHistoryList",
                    productDao.getProduct(Long.valueOf(productId.substring(1))).getPriceHistoryList());
        }
        request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
    }
}
