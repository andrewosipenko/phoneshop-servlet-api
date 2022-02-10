package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.*;
import com.es.phoneshop.model.recentView.HttpSessionRecentViewService;
import com.es.phoneshop.model.recentView.RecentView;
import com.es.phoneshop.model.recentView.RecentViewService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.NoSuchElementException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentViewService recentViewService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentViewService = HttpSessionRecentViewService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = null;
        try {
            String quantityString = request.getParameter("quantity");
            String url = request.getPathInfo().substring(1);
            productId = Long.parseLong(url);
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            int quantity = format.parse(quantityString).intValue();
            if (quantity < 1) {
                throw new IllegalArgumentException();
            }
            cartService.add(cartService.getCart(request), productId, quantity, request.getSession());
            response.sendRedirect(request.getContextPath() + "/products/" + productId + "?message=Product added to cart!");
        } catch (IllegalArgumentException | ParseException e) {
            request.setAttribute("error", "invalid quantity");
            response.sendRedirect(request.getContextPath() + "/products/" + productId + "?message=Error occurred while adding a product!");
        } catch (OutOfStockException e) {
            request.setAttribute("error", "not enough stock available");
            response.sendRedirect(request.getContextPath() + "/products/" + productId + "?message=Error occurred while adding a product!");
        } catch (NoSuchElementException | NullPointerException e) {
            request.setAttribute("error", "do not try to break my website");
            response.sendRedirect(request.getContextPath() + "/products/");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getPathInfo().substring(1);
        try {
            Product product = productDao.getProduct(Long.valueOf(url)).get();
            RecentView recentView = recentViewService.getRecentView(request);
            recentView.add(product);
            request.setAttribute("recentView", recentView.getDeque());
            request.setAttribute("product", product);
            request.setAttribute("cart", cartService.getCart(request));
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } catch (IllegalArgumentException | NoSuchElementException | NullPointerException e) {
            response.setStatus(404);
            request.setAttribute("incorrectId", url);
            request.getRequestDispatcher("/WEB-INF/pages/PageNotFound.jsp").forward(request, response);
        }
    }

}
