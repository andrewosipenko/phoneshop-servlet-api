package com.es.phoneshop.model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CartService {
    private static final String CART_ATTRIBUTE_NAME = "cart";
    private static CartService instance = new CartService();

    private CartService() {
    }

    public static CartService getInstance() {
        return instance;
    }

    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE_NAME);
        if (cart == null) {
            cart = new Cart();
            Cart finalCart = cart;
            ArrayListProductDao.getInstance().findProducts().forEach(product -> {add(finalCart, product, 1);});
        }
        return new Cart();
    }

    public void add(Cart cart, Product product, int quantity){
        cart.getCartItems().add(new CartItem(product, quantity));
    }
}

