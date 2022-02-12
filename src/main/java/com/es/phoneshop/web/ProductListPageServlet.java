package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortType;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long productId = Long.parseLong(request.getParameter("productId"));
            try {
                NumberFormat format = NumberFormat.getInstance(request.getLocale());
                int quantity = format.parse(request.getParameter("quantity")).intValue();
                Cart cart = HttpSessionCartService.getInstance().getCart(request);
                HttpSessionCartService.getInstance().add(cart, productId, quantity, request.getSession());
                response.sendRedirect(request.getContextPath() + "/products?message=Product added to cart!");
                return;
            } catch (NumberFormatException | ParseException e) {
                request.setAttribute("error", "Invalid quantity");
            } catch (OutOfStockException e) {
                request.setAttribute("error", "not enough stock available");
            }
            doGet(request, response);
        } catch (Exception e) {
            doGet(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortField = request.getParameter("sort");
        String sortType = request.getParameter("order");
        if (sortField == null) {
            sortField = "notSpecified";
        }
        if (sortType == null) {
            sortType = "asc";
        }
        try {
            request.setAttribute("products", productDao.findProducts(
                    query,
                    SortField.valueOf(sortField),
                    SortType.valueOf(sortType)));
        } catch (IllegalArgumentException e) {
            request.setAttribute("products", productDao.findProducts(
                    query,
                    SortField.notSpecified,
                    SortType.asc));
        }
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
