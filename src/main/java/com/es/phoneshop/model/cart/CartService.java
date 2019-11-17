package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.exception.LackOfStockException;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

public interface CartService {
    String add(HttpSession session, Cart cart, Long productId, String quantity, Locale locale)throws LackOfStockException,
            IllegalQuantityException, NumberFormatException;
    void calculateTotalPrice(Cart cart);
    void calculateTotalQuantity(Cart cart);
    Cart getCart(HttpSession session);
    Map<Long, String> update(HttpSession session, Cart cart, String[] productIds, String[] quantities, Locale locale)
            throws LackOfStockException, IllegalQuantityException, NumberFormatException;
    void delete(Cart cart, Long productId);
}
