package com.es.phoneshop.web;

import com.es.phoneshop.model.product.productdao.ArrayListProductDao;
import com.es.phoneshop.model.product.productdao.ProductDao;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.exceptions.QuantityLowerZeroException;
import com.es.phoneshop.model.product.exceptions.StockException;
import com.es.phoneshop.model.product.recentlyview.DefaultRecentlyViewService;
import com.es.phoneshop.model.product.recentlyview.RecentlyViewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {

    public static final String CART_PAGE_JSP = "/WEB-INF/pages/cartPage.jsp";
    private ProductDao productDao;
    private CartService cartService;
    private WebHelperService webHelperService;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        webHelperService = WebHelperService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher(CART_PAGE_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        Map<Long, String> errorsMap = new HashMap<>();
        for (int i = 0; i < productIds.length; i++) {
            Long productId = Long.parseLong(productIds[i]);
            int quantity;
            try {
                quantity = webHelperService.parseRightQuantity(request, quantities[i]);
                cartService.putToCart(cartService.getCart(request), productId, quantity);
            } catch (NumberFormatException | ParseException exception) {
                setErrorMessageToMap(productId, errorsMap, "Quantity should be integer");
            } catch (StockException exception) {
                setErrorMessageToMap(productId, errorsMap,
                        "Not enough stock, available " +
                                productDao.getProduct(productId).getStock());
            } catch (QuantityLowerZeroException exception) {
                setErrorMessageToMap(productId, errorsMap, "Quantity should be > 0");
            }
        }
        if (errorsMap.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?successMessage=Cart successfully updated");
        } else {
            request.setAttribute("errorsMap", errorsMap);
            doGet(request, response);
        }
    }

    private void setErrorMessageToMap(Long id, Map<Long, String> errorsMap, String errorMessage) {
        errorsMap.put(id, errorMessage);
    }
}