package com.es.phoneshop.web.pages;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;


public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;

    private CartService cartService;

    private Map<Long, String> errors;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        errors = new HashMap<>(1);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        SortField sortField = Optional.ofNullable(request.getParameter("sort")).map(SortField::valueOf).orElse(null);
        SortOrder sortOrder = Optional.ofNullable(request.getParameter("order")).map(SortOrder::valueOf).orElse(null);
        request.setAttribute("products", productDao.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quantity;
        String message;
        Long productId = Long.valueOf(request.getParameter("productId"));
        try {
            quantity = parseQuantity(request.getLocale(), request.getParameter("quantity"));
            verifyQuantity(quantity);
        } catch (ParseException e) {
            addError(request, productId, "Not a number");
            doGet(request, response);
            return;
        } catch (IllegalArgumentException e) {
            addError(request, productId, "Negative number or zero");
            doGet(request, response);
            return;
        }

        try {
            cartService.add(request, productId, quantity);
            message = "Product added to the cart";
        } catch (OutOfStockException e) {
            addError(request, productId, "Out of stock, only " + e.getStock() + " left");
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/products?message=" + message);
    }

    private void verifyQuantity(int quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private void addError(HttpServletRequest request, Long productId, String message) {
        errors.put(productId, message);
        request.setAttribute("errors", errors);
    }

    private int parseQuantity(Locale locale, String quantity) throws ParseException {
        return NumberFormat
                .getInstance(locale)
                .parse(quantity)
                .intValue();
    }
}
