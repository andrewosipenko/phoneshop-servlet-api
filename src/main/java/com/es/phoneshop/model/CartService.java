package com.es.phoneshop.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CartService {
    private static final String CART_ATTRIBUTE_NAME = "cart";
    private static CartService instance = new CartService();

    private CartService(){}

    private CartService getInstance(){
        return instance;
    }
    public Cart getCart(HttpServletRequest request){
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute(CART_ATTRIBUTE_NAME);
        if(cart == null){
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE_NAME, cart);
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
        finally {
            cartItems.add(new CartItem(product, quantity));
        }
    }
}
