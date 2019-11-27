package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Deque;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {
    public static final String VIEWED_PRODUCTS = "viewedProducts";
    public static final String PRODUCT = "product";
    public static final String ERROR_MESSAGE = "errorMessage";
    private ProductDao productDao;
    private ViewedProductsService viewedProducts;
    private static final String QUANTITY_PARAMETER = "quantity";
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        viewedProducts = HttpSessionViewedProductService.getInstance();
        cartService =  HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productDao.getProduct(extractId(request));
        Deque<Product> dequeViewedProducts = viewedProducts.getViewedProducts(request.getSession());
        request.setAttribute(VIEWED_PRODUCTS, dequeViewedProducts);
        viewedProducts.addViewedProducts(viewedProducts.getViewedProducts(request.getSession()), product);
        request.setAttribute(PRODUCT, productDao.getProduct(extractId(request)));
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Cart cart = cartService.getCart(request.getSession());
        Long productId = extractId(request);
        String quantity = request.getParameter(QUANTITY_PARAMETER);
        Locale locale = request.getLocale();
        try {
            String resultOfAddingProductInCart = cartService.add(request.getSession(), cart, productId, quantity, locale);
        }  catch (LackOfStockException e) {
            sendError("Error of stock! Max stock is " + productDao.getProduct(productId).getStock(),
                    request, response);
            return;
        }
        catch (IllegalQuantityException e) {
            sendError("Quantity should be greater then 0!", request, response);
            return;
        }
        catch (NumberFormatException e) {
            sendError("Quantity should be a number", request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?success=true");
    }

    private void sendError(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE, message);
        request.setAttribute(QUANTITY_PARAMETER, request.getParameter(QUANTITY_PARAMETER));
        doGet(request, response);
    }

    private Long extractId(HttpServletRequest request){
        return Long.parseLong(request.getPathInfo().replaceAll("/", ""));
    }
}
