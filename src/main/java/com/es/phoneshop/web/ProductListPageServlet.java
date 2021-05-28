package com.es.phoneshop.web;

import com.es.phoneshop.domain.common.model.SortingOrder;
import com.es.phoneshop.domain.product.model.ProductRequest;
import com.es.phoneshop.domain.product.persistence.ArrayListProductDao;
import com.es.phoneshop.domain.product.persistence.ProductDao;
import com.es.phoneshop.utils.LongIdGeneratorImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = new ArrayListProductDao(new LongIdGeneratorImpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("searchQuery");
        SortingOrder descriptionSort = SortingOrder.fromString(request.getParameter("descriptionSort"));
        SortingOrder priceSort = SortingOrder.fromString(request.getParameter("priceSort"));

        request.setAttribute("products", productDao.getAll(new ProductRequest(query, descriptionSort, priceSort)));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
