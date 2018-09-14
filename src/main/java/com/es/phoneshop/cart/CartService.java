package com.es.phoneshop.cart;

import com.es.phoneshop.model.Product;

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

    public synchronized Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE_NAME);
        if (cart == null) {
            cart = new Cart();
        }
        session.setAttribute(CART_ATTRIBUTE_NAME, cart);
        return cart;
    }

    public void add(Cart cart, Product product, int quantity) {
            CartItem newCartItem = new CartItem(product, quantity);
            if (cart.getCartItems().contains(newCartItem)) {
                increaseProductQuantity(cart, product, quantity);
            } else if(product.getStock() < quantity) {
                newCartItem.setQuantity(product.getStock());
                cart.getCartItems().add(newCartItem);
                product.setStock(0);
            } else {
                cart.getCartItems().add(newCartItem);
                product.setStock(product.getStock()-quantity);
            }
        }

    private void increaseProductQuantity(Cart cart, Product product, int quantity) {
            CartItem cartItem = cart.getCartItems().get(cart.getCartItems().indexOf(new CartItem(product, quantity)));
            if(product.getStock() >= quantity) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                product.setStock(product.getStock()-quantity);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + product.getStock());
                product.setStock(0);
            }
    }
}
