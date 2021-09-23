package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
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
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cartPage.jsp").forward(request, response);
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
                quantity = parseRightQuantity(request, quantities[i]);
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

    private int parseRightQuantity(HttpServletRequest request, String quantityString)
            throws NumberFormatException, QuantityLowerZeroException, ParseException {
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

    private void setErrorMessageToMap(Long id, Map<Long, String> errorsMap, String errorMessage) {
        errorsMap.put(id, errorMessage);
    }
}