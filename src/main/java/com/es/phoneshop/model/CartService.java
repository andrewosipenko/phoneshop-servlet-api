package com.es.phoneshop.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

public class CartService {
    private static final String CART_ATTRIBUTE_NAME = "cart";
    private static CartService instance = new CartService();

    private CartService(){}

    public static CartService getInstance(){
        return instance;
    }

    public Cart getCart(HttpServletRequest request){
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute(CART_ATTRIBUTE_NAME);
        synchronized (session) {
            if (cart == null) {
                cart = new Cart();
                add(cart, ArrayListProductDao.getInstance().findProducts().get(0), 1);
                add(cart, ArrayListProductDao.getInstance().findProducts().get(1), 1);
                session.setAttribute(CART_ATTRIBUTE_NAME, cart);
            }
        }
        return cart;
    }

    public void add(Cart cart, Product product, int quantity){
        List<CartItem> cartItems = cart.getCartItems();
        try {
            cartItems.stream()
                    .filter(item -> item.getProduct().equals(product))
                    .findAny().get().addQuantity(quantity);
        }
        catch (NoSuchElementException e){
            cartItems.add(new CartItem(product, quantity));
        }
    }
}
