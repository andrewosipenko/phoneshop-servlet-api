package com.es.phoneshop.web.actions;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.exceptions.InvalidInput;
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

public class AddToCartAction implements Action {
    private CartService cartService;
    private ProductDao productDao;

    public AddToCartAction(ServletConfig config) {
        //super.init(config);
        cartService = DefaultCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            return request.getContextPath() + "/products?message=Cart updated successfully";
        } else {
            request.setAttribute("errors", errors);
            return request.getContextPath() + "/products?errors="+errors.get(productId);
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
