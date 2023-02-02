package com.es.phoneshop.web.servlet;

import com.es.phoneshop.util.CartItemQuantityValidationUtil;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.HttpSessionCartService;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.service.DefaultRecentlyViewedProductsService;
import com.es.phoneshop.model.recentlyViewedProducts.RecentlyViewedProducts;
import com.es.phoneshop.service.RecentlyViewedProductsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private static final long serialVersionUID = 5487646042246219153L;

    private ProductDao productDao;

    private CartService cartService;

    private RecentlyViewedProductsService recentlyViewedProductsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentlyViewedProductsService = DefaultRecentlyViewedProductsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdString = request.getPathInfo().substring(1);
        Long productId = Long.valueOf(productIdString);
        request.setAttribute("product", productDao.findById(productId));
        request.setAttribute("cart", cartService.getCart(request));

        RecentlyViewedProducts recentlyViewedProducts = recentlyViewedProductsService.getProducts(request);
        recentlyViewedProductsService.add(productId, request);
        request.setAttribute("recently_viewed", recentlyViewedProducts);

        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = parseProductId(request);
        String quantityString = request.getParameter("quantity");

        String errorMessage = CartItemQuantityValidationUtil
                .validateQuantity(request, quantityString);

        if(errorMessage != null)
        {
            incorrectQuantityError(request, response, errorMessage);
            return;
        }

        int quantity = CartItemQuantityValidationUtil.parseQuantity(request, quantityString).intValue();
        try {
            cartService.add(productId, quantity, request);
        } catch (OutOfStockException e) {
            incorrectQuantityError(request, response, "Out of stock, available " + e.getStock());
            return;
        }

        request.setAttribute("message", "Product added to cart");
        response.sendRedirect(request.getContextPath() + "/products/" + productId + "?message=Product added to cart");
    }

    private void incorrectQuantityError(HttpServletRequest request, HttpServletResponse response,
                                        String errorMessage) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        doGet(request, response);
    }

    private Long parseProductId(HttpServletRequest request) {
        String productInfo = request.getPathInfo().substring(1);
        return Long.valueOf(productInfo);
    }
}
