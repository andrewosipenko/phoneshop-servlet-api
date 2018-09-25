package com.es.phoneshop.model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class CartService implements Serializable{
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
            session.setAttribute(CART_ATTRIBUTE_NAME, cart);
        }
        return cart;
    }

    public void delete(Cart cart, Product product){
        List<CartItem> list = cart.getCartItems();
        for(int i = 0; i < list.size(); i++){
            if(product.equals(list.get(i).getProduct())){
                list.remove(i);
                break;
            }
        }
    }


    public void add(Cart cart, Product product, int quantity){
        addOrUpdate(cart, product, quantity, true);

    }

    public void update(Cart cart, Product product, int quantity){
        addOrUpdate(cart, product, quantity, false);
    }


    private void addOrUpdate(Cart cart, Product product, int quantity, boolean add){
        List<CartItem> cartItems = cart.getCartItems();
        Optional<CartItem> cartItemOptional = cartItems.stream()
                .filter(p -> p.getProduct().equals(product))
                .findAny();
        if(cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            int updateQuantity = add ? cartItem.getQuantity() + quantity : quantity;
            cartItem.setQuantity(updateQuantity);
        }
        else{
            cart.getCartItems().add(new CartItem(product, quantity));
        }
    }

}

