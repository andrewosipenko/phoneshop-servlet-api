package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.exception.LackOfStockException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public interface CartService {
    String add(Cart cart, Long productId, String quantity, Locale locale)throws LackOfStockException,
            IllegalQuantityException, NumberFormatException;
    void calculateTotalPrice(Cart cart);
    void calculateTotalQuantity(Cart cart);
    Cart getCart(HttpServletRequest request);
}
