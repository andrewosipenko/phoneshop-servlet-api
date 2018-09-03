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
    public synchronized Cart getCart(HttpServletRequest request){
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE_NAME);
        if (cart == null){
            cart = new Cart();
            for (Product product : ArrayListProductDAO.getInstance().findProducts()){
                add(cart, product, 1);
            }
            session.setAttribute(CART_ATTRIBUTE_NAME, cart);
        }
        return cart;
    }

    public synchronized void add(Cart cart, Product product, int quantity){
        if (quantity <= product.getStock()) {
            CartItem cartItem = new CartItem(product, quantity);
            int position = cart.getCartItems().indexOf(cartItem);
            if(position == -1){
                cart.getCartItems().add(new CartItem(product, quantity));
            }
            else{
                cartItem.setQuantity(quantity + cart.getCartItems().get(position).getQuantity());
                cart.getCartItems().set(position, cartItem);
            }
        } else {
            throw new IllegalArgumentException("Not enough products in stock.");
        }
    }
}
