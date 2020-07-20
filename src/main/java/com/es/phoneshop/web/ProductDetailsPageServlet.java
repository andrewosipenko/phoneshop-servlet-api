package com.es.phoneshop.web;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productInfo = request.getPathInfo().substring(1);
        String[] words = productInfo.split("/");
        Long id = null;
        try {
            if (words[0].equals("priceHistory")) {
                id = Long.valueOf(words[1]);
                request.setAttribute("product", productDao.getProduct(id));
                request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
            } else {
                id = Long.valueOf(words[0]);
                request.setAttribute("product", productDao.getProduct(id));
                request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            }
        }
        catch (RuntimeException ex) {
            request.setAttribute("id", id.toString());
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
        }
    }

}
