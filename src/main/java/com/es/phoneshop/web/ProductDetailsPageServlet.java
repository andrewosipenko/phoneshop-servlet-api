package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.RecentlyViewedService;
import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.service.impl.DefaultRecentlyViewedService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private DefaultCartService cartService;
    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init(ServletConfig config) {
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentlyViewedService = DefaultRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.valueOf(request.getPathInfo().substring(1));
        Cart cart = cartService.getCart(request);
        Product product = productDao.getProduct(productId);

        recentlyViewedService.addProduct(request, product);

        request.setAttribute("product", product);
        request.setAttribute("cart", cart);

        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        long productId = Long.parseLong(request.getPathInfo().substring(1));
        String quantityString = request.getParameter("quantity");
        int quantity;
        try {
            NumberFormat numberFormat = NumberFormat.getInstance(request.getLocale());
            quantity = numberFormat.parse(quantityString).intValue();
            cartService.add(cart, productId, quantity);
        } catch (ParseException e) {
            request.setAttribute("error", "Not a number");
            doGet(request, response);
            return;
        } catch (OutOfStockException e) {
            request.setAttribute("error", "Not enough stock, available " + e.getStockAvailable());
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?success=true");
    }
}
