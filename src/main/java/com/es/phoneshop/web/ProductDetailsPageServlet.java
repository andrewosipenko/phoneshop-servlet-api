package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.lastViewed.LastViewed;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductListService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductListService productListService;
    private CartService cartService;
    private LastViewed lastViewed;

    private long getProductId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response, Product product) throws ServletException, IOException {
        request.setAttribute("product", product);
        request.setAttribute("lastViewed", lastViewed.getLastViewed());
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    public void init() {
        productListService = new ProductListService();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        lastViewed = new LastViewed(request.getSession());

        try {
            lastViewed.add(productListService.getProduct(getProductId(request)));

            showPage(request, response, productListService.getProduct(getProductId(request)));
        } catch (ProductNotFoundException e) {
            response.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productListService.getProduct(getProductId(request));

        String error = null;
        try {
            Locale locale = request.getLocale();
            String quantityString = request.getParameter("quantity");
            int quantity = NumberFormat.getNumberInstance(locale).parse(quantityString).intValue();
            cartService.addProduct(cartService.getCart(request.getSession()), product, quantity);
        } catch (ParseException e) {
            error = "Not a number";
        } catch (OutOfStockException e) {
            error = e.getMessage();
        }

        if (error != null) {
            request.setAttribute("error", error);
            showPage(request, response, product);
            return;
        }

        response.sendRedirect(request.getRequestURI() + "?message=Added to cart successfully");
    }
}
