package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.DefaultCartService;
import com.es.phoneshop.model.product.*;
import com.es.phoneshop.recentViewd.RecentViewed;
import com.es.phoneshop.recentViewd.RecentViewedService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
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
            if(cartService.getCart(request).getCartItemByProductId(Long.valueOf(productId)).isPresent()){
                request.setAttribute("message","You have " +
                        cartService.getCart(request).getCurrentQuantityById(Long.valueOf(productId))
                + " in cart" );
            }else {
                request.setAttribute("message","");
            }
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
        String productId = request.getPathInfo().substring(1);
        String quantity = request.getParameter("quantity");
        Product product = productDao.getProduct(Long.valueOf(productId)).orElse(null);

        request.setAttribute("product", product);
        request.setAttribute("recentViewedList", recentViewed.getRecentViewedList(request).getItems());
        request.setAttribute("cart", cartService.getCart(request).getItems());

        if (!isProductIdExist(productId) || product == null) {
            response.setStatus(404);
            request.setAttribute("id", productId);
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
            return;
        }

        int quantityInt;
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        try {
            quantityInt = format.parse(quantity).intValue();
        } catch (ParseException e) {
            request.setAttribute("error", "Not a number");
            request.setAttribute("message","Product not added to cart.\n" +
                    "You have " +
                    cartService.getCart(request).getCurrentQuantityById(Long.valueOf(productId)) +
                    " now."
            );
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
            return;
        }

        if(quantityInt < 0){
            request.setAttribute("error", "Negative amount");
            request.setAttribute("message","Product not added to cart.\n" +
                    "You have " +
                    cartService.getCart(request).getCurrentQuantityById(Long.valueOf(productId)) +
                    " now."
            );            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
            return;
        }

        if(quantityInt == 0){
            request.setAttribute("error", "Product not added to cart, because amount is 0");
            request.setAttribute("message","Product not added to cart.\n" +
                    "You have " +
                    cartService.getCart(request).getCurrentQuantityById(Long.valueOf(productId)) +
                    " now."
            );            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
            return;
        }

        if (!cartService.isEnoughStockForOrder(request,product,quantityInt)) {
            request.setAttribute("error", "Out of stock available " +
                    (product.getStock() -
                            cartService.getCart(request).getCurrentQuantityById(Long.valueOf(productId))));
            request.setAttribute("message","Product not added to cart.\n" +
                    "You have " +
                    cartService.getCart(request).getCurrentQuantityById(Long.valueOf(productId)) +
                    " now."
            );            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } else {
                Cart cart = cartService.getCart(request);
                cartService.add(request, cart, Long.valueOf(productId), quantityInt);
                response.sendRedirect(request.getContextPath() + "/products/" + productId);
        }
    }
}

