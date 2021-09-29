package com.es.phoneshop.web;

import com.es.phoneshop.model.product.productdao.ArrayListProductDao;
import com.es.phoneshop.model.product.productdao.Product;
import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.productdao.ProductDao;
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
    public static final String PRODUCT_DETAILS_PAGE_JSP = "/WEB-INF/pages/productDetails.jsp";

    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewService recentlyViewService;
    private WebHelperService webHelperService;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentlyViewService = DefaultRecentlyViewService.getInstance();
        webHelperService = WebHelperService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long productId;
        try {
            productId = webHelperService.parseProductId(request);
        } catch (NumberFormatException exception) {
            throw new ProductNotFindException("There is no product with " + request.getPathInfo().substring(1) + " id");
        }
        Product product = productDao.getProduct(productId);
        RecentlyViewSection recentlyViewSection = recentlyViewService.getRecentlyViewSection(request);
        recentlyViewService.add(recentlyViewSection, request, product);
        request.setAttribute(RECENTLY_VIEW_SECTION, recentlyViewSection);
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("product", product);
        request.getRequestDispatcher(PRODUCT_DETAILS_PAGE_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quantity;
        try {
            String quantityString = request.getParameter(QUANTITY);
            quantity = webHelperService.parseRightQuantity(request, quantityString);
        } catch (NumberFormatException | ParseException exception) {
            setErrorMessage(request, response, "Quantity should be integer");
            return;
        } catch (QuantityLowerZeroException exception) {
            setErrorMessage(request, response, "Quantity should be > 0");
            return;
        }
        long productId = webHelperService.parseProductId(request);
        Cart cart = cartService.getCart(request);
        try {
            cartService.addToCart(cart, productId, quantity);
        } catch (StockException e) {
            setErrorMessage(request, response,
                    "Not enough stock, available " + productDao.getProduct(productId).getStock());
            return;
        }
        response.sendRedirect(request.getContextPath() + "/products/" +
                productId + "?successMessage=Product added to cart");
    }

    private void setErrorMessage(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, errorMessage);
        doGet(request, response);
    }
}
