package com.es.phoneshop.service.impl;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class CartServiceImpl implements CartService {

    private final String CART_SESSION_ATTRIBUTE = "cart";
    private static CartServiceImpl instance;

    private CartServiceImpl() {

    }

    public static synchronized CartServiceImpl getInstance() {
        if (instance == null) {
            instance = new CartServiceImpl();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        synchronized (session) {
            return (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        }
    }

    @Override
    public void addToCart(Product product, int quantity, HttpServletRequest request) throws OutOfStockException {
        HttpSession session = request.getSession();
        synchronized (session) {
            Cart cart = getCart(request);
            Optional<CartItem> existingCartItem = cart.getItems()
                    .stream()
                    .filter(item -> item.getProduct().equals(product))
                    .findAny();

            if (existingCartItem.isPresent()) {
                CartItem cartItem = existingCartItem.get();
                int currentQuantity = quantity + cartItem.getQuantity();
                if (product.getStock() < currentQuantity) {
                    throw new OutOfStockException(product, quantity, product.getStock() - cartItem.getQuantity());
                }
                cartItem.setQuantity(currentQuantity);
            } else {
                if (product.getStock() < quantity) {
                    throw new OutOfStockException(product, quantity, product.getStock());
                }
                cart.getItems().add(new CartItem(product, quantity));
            }
        }
    }

    public String getCartSessionAttribute() {
        return CART_SESSION_ATTRIBUTE;
    }
}
