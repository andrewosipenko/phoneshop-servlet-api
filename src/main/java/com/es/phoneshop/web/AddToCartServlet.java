package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.exceptions.*;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletConfig;
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

public class AddToCartServlet extends HttpServlet {
    private CartService cartService;
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<Long, String> errors = new HashMap<>();
        Long productId = Long.valueOf(request.getPathInfo().substring(1));
        String[] quantities = request.getParameterValues("quantity");
        String[] productIds = request.getParameterValues("productId");
        int index = 0;
        for (int i = 0; i < productIds.length; i++) {
            if (productId == Long.valueOf(productIds[i])) {
                index = i;
            }
        }
        try {
            isQuantityCorrect(quantities[index]);
            Integer quantity = Integer.valueOf(quantities[index]);
            cartService.update(cartService.getCart(request.getSession()), productId, quantity);
        } catch (OutOfStockException | NegativeQuantityException | ZeroQuantityException | InvalidInput e) {
            handleError(errors, productId, e);
        }
        if (errors.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products?message=Cart updated successfully");
        } else {
            request.setAttribute("errors", errors);
            response.sendRedirect(request.getContextPath() + "/products?errors="+errors.get(productId));
        }
    }

    private int getQuantity(HttpServletRequest request, String quantityString) throws ParseException {
        NumberFormat numberFormat = getNumberFormat(request.getLocale());
        return numberFormat.parse(quantityString).intValue();
    }

    protected NumberFormat getNumberFormat(Locale locale) {
        return NumberFormat.getInstance(locale);
    }

    private void handleError(Map<Long, String> errors, Long productId, Exception exception) {
        if (exception.getClass().equals(ZeroQuantityException.class)) {
            errors.put(productId, "Zero quantity");
        } else if (exception.getClass().equals(NegativeQuantityException.class)) {
            errors.put(productId, "Negative quantity");
        } else if (exception.getClass().equals(OutOfStockException.class)) {
            Product product = productDao.getProduct(productId);
            int inStock = product.getStock();
            errors.put(productId, "Out of stock, max available " + inStock);
        } else if(exception.getClass().equals(InvalidInput.class)){
            errors.put(productId, "Invalid input");
        }
    }

    public boolean isQuantityCorrect(String quantity) throws InvalidInput {
        if(!quantity.isEmpty()){
            if(quantity.matches("[1-9]")){
                return true;
            }
        }
        throw new InvalidInput();
    }
}
