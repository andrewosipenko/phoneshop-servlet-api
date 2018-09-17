package com.es.phoneshop.model;

import com.es.phoneshop.exceptions.NotEnoughException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
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

    private void addOrUpdate(Cart cart, Product product, int quantity, boolean add){
        if (product.getStock() < quantity) {
            throw new NotEnoughException(NotEnoughException.NOT_ENOUGH_MESSAGE);
        }
        List<CartItem> cartItems = cart.getCartItems();
        Optional<CartItem> cartItemOptional = cartItems.stream()
                .filter(ci -> ci.getProduct().equals(product))
                .findAny();
        if(cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            int newQuantity = add ? cartItem.getQuantity() + quantity : quantity;
            cartItem.setQuantity(newQuantity);
        }
        else{
            cart.getCartItems().add(new CartItem(product, quantity));
        }
    }

    public void add(Cart cart, Product product, int quantity){
        addOrUpdate(cart, product, quantity, true);
    }

    public void update(Cart cart, Product product, int quantity){
        addOrUpdate(cart, product, quantity, false);
    }

    public void delete(Cart cart, Product product) {
        synchronized (cart) {
            List<CartItem> cartItems = cart.getCartItems();
            cartItems.remove(new CartItem(product, 0));
        }
    }
}
