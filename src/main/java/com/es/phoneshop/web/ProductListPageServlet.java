package com.es.phoneshop.web;

import com.es.phoneshop.model.product.DAO.ArrayListProductDao;
import com.es.phoneshop.model.product.DAO.ProductDao;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ProductListPageServlet extends HttpServlet {
    public ProductDao productDao;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        productDao = new ArrayListProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productDao.findProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        productDao.findProducts().forEach(product -> product.setIsChosen(false));
        switch (action) {
            case ("findProduct"):
                if (request.getParameter("phoneId") != null && !Objects.equals(request.getParameter("phoneId"), "")) {
                    try {
                        productDao.getProduct(Long.valueOf(request.getParameter("phoneId"))).setIsChosen(true);
                    } catch (NoSuchElementException exception) {
                        exception.printStackTrace();
                    }
                }
                break;
            case ("deleteProducts"):
                if (request.getParameter("phoneIdToDelete") != null && !Objects.equals(request.getParameter("phoneIdToDelete"), "")) {
                    productDao.delete(Long.valueOf(request.getParameter("phoneIdToDelete")));
                }
                break;
            case ("findNotNullProducts"):
                productDao.findProducts().forEach(product -> product.setIsChosen(true));
                break;
        }

        request.setAttribute("products", productDao.findProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
