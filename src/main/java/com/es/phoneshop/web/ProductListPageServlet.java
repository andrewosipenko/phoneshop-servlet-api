package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {
    private static final String SEARCH = "search";
    private static final String SORT_BY = "sortBy";
    private static final String ORDER = "order";
    public static final String PRODUCTS = "products";
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(PRODUCTS, getProducts(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private List<Product> getProducts(HttpServletRequest request) {
        String searchValue = request.getParameter(SEARCH);
        List<Product> products;
        if (searchValue != null && !searchValue.isEmpty()) {
            products = productDao.findProductsByDescription(searchValue);
        } else {
            products = productDao.findProducts();
        }
        String sortParameter = request.getParameter(SORT_BY);
        if (sortParameter != null) {
            products = productDao.sort(products, sortParameter, request.getParameter(ORDER));
        }
        return products;
    }

}
