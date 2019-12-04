package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

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

public class CartPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    private static final String PRODUCT = "product";
    private static final String CART = "cart";
    private static final String QUANTITY = "quantity";
    private static final String ERROR = "error";
    private static final String PRODUCT_ID = "productId";
    private static final String ERROR_MAP = "errorMap";

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);

        request.setAttribute(CART, cart);

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIdStrings = request.getParameterValues(PRODUCT_ID);
        String[] quantityStrings = request.getParameterValues(QUANTITY);

        Cart cart = cartService.getCart(request);
        Locale locale = request.getLocale();
        Map<Product, String> errorMap = new HashMap<>();

        for (int i = 0; i < productIdStrings.length; i++) {
            long productId = Long.valueOf(productIdStrings[i]);
            Product product = productDao.getProduct(productId);

            addToCart(cart, product, quantityStrings[i], locale, errorMap);
        }

        if (errorMap.isEmpty()) {
            response.sendRedirect(request.getRequestURI() + "?success=true");
        } else {
            request.setAttribute(ERROR_MAP, errorMap);
            request.setAttribute(CART, cart);
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
        }
    }

    private void addToCart(Cart cart, Product product, String quantityString, Locale locale, Map<Product, String> errorMap) throws ServletException, IOException {
        try {
            int quantity = getQuantity(locale, quantityString);
            cartService.update(cart, product, quantity);
        } catch (ParseException e) {
            errorMap.put(product, "Not a number");
        } catch (OutOfStockException e) {
            errorMap.put(product, "Not enough stock. Available " + e.getAvailableStock());
        }
    }

    private int getQuantity(Locale locale, String quantityString) throws ParseException {
        return NumberFormat.getNumberInstance(locale).parse(quantityString).intValue();
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}