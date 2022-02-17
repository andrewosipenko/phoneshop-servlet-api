package com.es.phoneshop.web;

import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.DefaultCartService;
import com.es.phoneshop.exceptions.IncorrectInputException;
import com.es.phoneshop.model.product.*;
import com.es.phoneshop.recentViewed.RecentViewed;
import com.es.phoneshop.recentViewed.RecentViewedService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentViewed recentViewed;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentViewed = RecentViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo().substring(1);
        if (!isProductIdExist(productId)) {
            response.setStatus(404);
            request.setAttribute("id", productId);
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
        } else {
            recentViewed.addToRecentViewed(request, productDao.getProduct(Long.valueOf(productId)).get());
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        }
    }

    private boolean isProductIdExist(String productId) {
        if (Pattern.matches("^[0-9]+$", productId)) {
            return productDao.getProduct(Long.valueOf(productId)).isPresent();
        } else {
            return false;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() != null &&
                isProductIdExist(request.getPathInfo().substring(1))) {
            try {
                String productId = request.getPathInfo().substring(1);
                String quantity = request.getParameter("quantity");
                cartService.add(request, productId, quantity);
                response.sendRedirect(request.getContextPath() + "/products/" + productId +
                        "?message=Product was added to cart");
            } catch (IncorrectInputException e) {
                Product product = productDao.getProduct(Long.valueOf(request.getPathInfo().substring(1))).orElse(null);
                String errorMessage = e.getErrorMessage();
                if (errorMessage.equals("Out of stock")) {
                    errorMessage = errorMessage + ", available " + (product.getStock() -
                            cartService.getCart(request).getCurrentQuantityById(product.getId()));
                }
                request.setAttribute("error", errorMessage);
                request.setAttribute("product", product);
                request.setAttribute("cart", cartService.getCart(request).getItems());
                request.setAttribute("recentViewedList", recentViewed.getRecentViewedList(request).getItems());
                request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
            }
        } else { //product not exist or not found case
            response.setStatus(404);
            if (request.getPathInfo() != null) {
                request.setAttribute("id", request.getPathInfo().substring(1));
            }else {
                request.setAttribute("id", "");
            }
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
        }
    }
}

