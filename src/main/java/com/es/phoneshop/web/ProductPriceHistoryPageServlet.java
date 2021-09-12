package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductPriceHistoryPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo();
        if (productId == null) {
            request.setAttribute("product", null);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        } else {
            Optional<Product> result = productDao.getProduct(Long.valueOf(productId.substring(1)));
            if (result.isPresent()) {
                request.setAttribute("product", result.get());
                request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
            } else {
                request.setAttribute("product", null);
                request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
            }
        }
    }
}
