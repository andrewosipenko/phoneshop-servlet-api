package com.es.phoneshop.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private static volatile CartServiceImpl instance;
    private static final String CART_ATTRIBUTE_NAME = "cart";

    private CartServiceImpl() {}
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
    public Cart getCart(HttpServletRequest request){
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE_NAME);
        if (cart == null){
            synchronized (session){
                if(session.getAttribute(CART_ATTRIBUTE_NAME) == null){
                    cart = new Cart();
                    session.setAttribute(CART_ATTRIBUTE_NAME, cart);
                    for (Product product : ArrayListProductDAO.getInstance().findProducts()){
                        add(cart, product, 1);
                    }
                }
            }
        }
        return cart;
    }

    public void add(Cart cart, Product product, int quantity){
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().equals(product))
                .findAny();
        if(!existingItem.isPresent()){
            if(product.getStock() >= quantity){
                cart.getCartItems().add(new CartItem(product, quantity));
                return;
            }
        }
        existingItem
                .filter(ci -> ci.getQuantity() + quantity <= product.getStock())
                .ifPresent(ci -> ci.setQuantity(ci.getQuantity() + quantity));
    }
}
