package com.es.phoneshop.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CartService {
    private static volatile CartService instance;
    private static final String CART_ATTRIBUTE_NAME = "cart";

    private CartService() {}

    public static CartService getInstance() {
        CartService localInstance = instance;
        if (localInstance == null) {
            synchronized (CartService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CartService();
                }
            }
        }
        return localInstance;
    }

    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE_NAME);
        if (cart == null) {
            cart = new Cart();
            for (Product product: ArrayListProductDao.getInstance().findProducts()) {
                add(cart, product, product.getStock());
            }
        }
        session.setAttribute(CART_ATTRIBUTE_NAME, cart);
        return cart;
    }

    public void add(Cart cart, Product product, int quantity) {
        if (product.getStock() >= quantity) {
            CartItem newCartItem = new CartItem(product, quantity);
            if (cart.getCartItems().contains(newCartItem))
                cart.getCartItems().get(cart.getCartItems().indexOf(newCartItem)).increaseQuantity(quantity);
            else
                cart.getCartItems().add(newCartItem);
        }
    }
}
