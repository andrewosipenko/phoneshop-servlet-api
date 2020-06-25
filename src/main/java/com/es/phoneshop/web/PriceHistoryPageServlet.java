package com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PriceHistoryPageServlet extends HttpServlet {

    ProductDao productDao;

    @Override
    public void init() throws ServletException {
        System.out.println("working priceHistoryPageServlet init method");
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Long productId = Long.parseLong(pathInfo.split("/")[1]);
        Product product = productDao.getProduct(productId);
        req.setAttribute("product",product);
        req.getRequestDispatcher("/WEB-INF/pages/priceHistory.jsp").forward(req,resp);
    }
}
