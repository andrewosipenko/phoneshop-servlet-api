package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recentlyViewed.HttpSessionViewedProductsService;
import com.es.phoneshop.model.recentlyViewed.RecentlyViewedProducts;
import com.es.phoneshop.model.recentlyViewed.ViewedProductsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private ViewedProductsService viewedProductsService;

    private final String PRODUCT = "product";
    private final String CART = "cart";
    private final String QUANTITY = "quantity";
    private final String ERROR = "error";

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        viewedProductsService = HttpSessionViewedProductsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productID = getProductID(request);
        Product product = productDao.getProduct(productID);

        RecentlyViewedProducts viewedProducts = viewedProductsService.getViewedProducts(request);
        viewedProductsService.add(viewedProducts, product);
        request.getSession().setAttribute(HttpSessionViewedProductsService.VIEWED_PRODUCTS, viewedProducts);

        showPage(request, response, product);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productID = getProductID(request);
        Product product = productDao.getProduct(productID);

        addToCart(request, response, product);
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response, Product product) throws ServletException, IOException {
        String error;
        try {
            int quantity = getQuantity(request);
            Cart cart = cartService.getCart(request);
            cartService.add(cart, product, quantity);
        } catch (ParseException | OutOfStockException e) {
            if (e instanceof ParseException) {
                error = "Not a number";
            } else {
                error = "Not enough stock. Available " + ((OutOfStockException) e).getAvailableStock();
            }
            request.setAttribute(ERROR, error);
            showPage(request, response, product);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?success=true");
    }

    private int getQuantity(HttpServletRequest request) throws ParseException {
        Locale locale = request.getLocale();
        String quantityString = request.getParameter(QUANTITY);
        return NumberFormat.getNumberInstance(locale).parse(quantityString).intValue();
    }

    private long getProductID(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String productID = uri.substring(uri.lastIndexOf("/") + 1);
        return Long.parseLong(productID);
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response, Product product) throws ServletException, IOException {
        request.setAttribute(PRODUCT, product);
        request.setAttribute(CART, cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }
}
