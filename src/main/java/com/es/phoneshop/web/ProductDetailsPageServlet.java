package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.exceptions.ProductNotFindException;
import com.es.phoneshop.model.product.exceptions.QuantityLowerZeroException;
import com.es.phoneshop.model.product.exceptions.StockException;
import com.es.phoneshop.model.product.recentlyview.DefaultRecentlyViewService;
import com.es.phoneshop.model.product.recentlyview.RecentlyViewSection;
import com.es.phoneshop.model.product.recentlyview.RecentlyViewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {

    public static final String QUANTITY = "quantity";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String RECENTLY_VIEW_SECTION = "recentlyViewSection";

    ProductDao productDao;
    CartService cartService;
    RecentlyViewService recentlyViewService;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentlyViewService = DefaultRecentlyViewService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productId;
        try {
            productId = parseProductId(request);
        } catch (NumberFormatException exception) {
            throw new ProductNotFindException("There is no product with " + request.getPathInfo().substring(1) + " id");
        }
        Product product = productDao.getProduct(productId);
        RecentlyViewSection recentlyViewSection = recentlyViewService.getRecentlyViewSection(request);
        recentlyViewService.add(recentlyViewSection, request, product);
        request.setAttribute(RECENTLY_VIEW_SECTION, recentlyViewService.getRecentlyViewSection(request));
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quantity;
        try {
            quantity = parseRightQuantity(request);
        } catch (NumberFormatException | ParseException exception) {
            setErrorMessage(request, response, "Quantity should be integer");
            return;
        } catch (QuantityLowerZeroException exception) {
            setErrorMessage(request, response, "Quantity should be > 0");
            return;
        }
        long productId = parseProductId(request);
        Cart cart = cartService.getCart(request);
        try {
            cartService.add(cart, productId, quantity);
        } catch (StockException e) {
            setErrorMessage(request, response, "Not enough stock");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/products/" +
                productId + "?successMessage=Product added to cart");
    }

    private Long parseProductId(HttpServletRequest request) {

        return Long.parseLong(request.getPathInfo().substring(1));
    }

    private int parseRightQuantity(HttpServletRequest request) throws NumberFormatException, QuantityLowerZeroException, ParseException {
        String quantityString = request.getParameter(QUANTITY);
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        int quantity = getQuantity(quantityString, format);
        if (quantity <= 0) {
            throw new QuantityLowerZeroException("Quantity should be > 0");
        }
        return quantity;
    }

    private int getQuantity(String quantityString, NumberFormat format) throws ParseException {
        int quantity = format.parse(quantityString).intValue();
        double quantityDouble = format.parse(quantityString).doubleValue();
        if (quantityDouble % 1 != 0) {
            throw new NumberFormatException("Quantity should be integer");
        }
        return quantity;
    }

    private void setErrorMessage(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        doGet(request, response);
    }
}
