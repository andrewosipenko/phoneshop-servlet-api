package com.es.phoneshop.web.actions;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.exceptions.NegativeQuantityException;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.exceptions.ZeroQuantityException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.web.command.Action;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartPageAction implements Action {
    private static final String JSP_PATH = "/WEB-INF/pages/cart.jsp";

    private ProductDao productDao;
    private CartService cartService;

    public CartPageAction(ServletConfig config) {
        //super.init(config);
        cartService = DefaultCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    public String executeGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request.getSession()));

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
        return "";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Long, String> errors = new HashMap<>();
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        String message="Cart updated successfully";

        if(productIds!=null){
            for (int i = 0; i < productIds.length; i++) {
                Long productId = Long.valueOf(productIds[i]);

                int quantity;
                try {
                    quantity = getQuantity(request, quantities[i]);
                    cartService.update(cartService.getCart(request.getSession()), productId, quantity);
                } catch (ParseException | OutOfStockException | NegativeQuantityException | ZeroQuantityException e) {
                    handleError(errors, productId, e, request);
                }
            }
        }
        if(errors.isEmpty()){
            if(productIds==null){
                message = "There is nothing to update";
            }
            return request.getContextPath() + "/cart?message="+message;
        }else{
            request.setAttribute("errors", errors);
            executeGet(request, response);
        }
        return "";
    }

    private int getQuantity(HttpServletRequest request, String quantityString) throws ParseException {
        NumberFormat numberFormat = getNumberFormat(request.getLocale());
        return numberFormat.parse(quantityString).intValue();
    }

    protected NumberFormat getNumberFormat(Locale locale) {
        return NumberFormat.getInstance(locale);
    }

    private void handleError(Map<Long, String> errors, Long productId, Exception exception, HttpServletRequest request) {
        if (exception.getClass().equals(ParseException.class)) {
            errors.put(productId, "Not a number");
        } else if (exception.getClass().equals(NegativeQuantityException.class)) {
            errors.put(productId, "Negative quantity");
        } else if (exception.getClass().equals(OutOfStockException.class)) {
            Product product = productDao.getProduct(productId);
            int inStock = product.getStock();
            errors.put(productId, "Out of stock, max available " + inStock);
        }
    }
}
