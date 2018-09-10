package com.es.phoneshop.model;

import com.es.phoneshop.exceptions.NotEnoughException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private static volatile CartServiceImpl instance;
    private static final String CART_ATTRIBUTE_NAME = "cart";

    private CartServiceImpl() {
    }

    public static CartServiceImpl getInstance() {
        CartServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (CartServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CartServiceImpl();
                }
            }
        }
        return localInstance;
    }

    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE_NAME);
        if (cart == null) {
            synchronized (session) {
                if (session.getAttribute(CART_ATTRIBUTE_NAME) == null) {
                    cart = new Cart();
                    session.setAttribute(CART_ATTRIBUTE_NAME, cart);
                }
            }
        }
        return cart;
    }

    public void add(Cart cart, Product product, int quantity) throws NotEnoughException{
        if (product.getStock() < quantity) {
            throw new NotEnoughException(NotEnoughException.NOT_ENOUGH_MESSAGE);
        }
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream().filter(cartItem -> cartItem.getProduct().equals(product)).findAny();
        if (!optionalCartItem.isPresent()) {
            product.setStock(product.getStock() - quantity);
            cart.getCartItems().add(new CartItem(product, quantity));
        } else {
            optionalCartItem = optionalCartItem.filter(cartItem -> cartItem.getQuantity() + quantity <= cartItem.getProduct().getStock());
            if (!optionalCartItem.isPresent()) {
                throw new NotEnoughException(NotEnoughException.NOT_ENOUGH_MESSAGE);
            }
            product.setStock(product.getStock() - quantity);
            optionalCartItem.get().setProduct(product);
            optionalCartItem.get().setQuantity(optionalCartItem.get().getQuantity() + quantity);
        }
    }
}
