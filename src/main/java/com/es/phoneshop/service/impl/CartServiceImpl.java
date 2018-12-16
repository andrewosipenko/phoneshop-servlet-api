package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.CartService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    private static final String CART_ATTRIBUTE = "cart";
    private static volatile CartService cartService;

    private CartServiceImpl(){}

    public static CartService getInstance(){
        if(cartService == null)
            synchronized (CartServiceImpl.class){
                if(cartService == null)
                    cartService = new CartServiceImpl();
            }
        return cartService;
    }

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart)session.getAttribute(CART_ATTRIBUTE);
        if(cart == null){
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }


    @Override
    public void addToCart(Cart cart, Product product, Integer quantity) {
        Long productId = product.getId();
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findAny();

        if(cartItemOptional.isPresent()){
            CartItem cartItem = cartItemOptional.get();
            if(quantity <= product.getStock()) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                product.setStock(product.getStock() - quantity);
            }else{
                throw new NoSuchElementException();
            }
        }else{
            if(quantity <= product.getStock()){
                cart.getCartItems().add(new CartItem(product, quantity));
                product.setStock(product.getStock() - quantity);
            }else{
                throw new NoSuchElementException();
            }
        }
        recalculateCart(cart);
    }

    @Override
    public void updateCart(Cart cart, Product product, int quantity){
        Long productId = product.getId();
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findAny();
        if(cartItemOptional.isPresent()){
            cartItemOptional.get().setQuantity(quantity);
        }else{
            throw new NoSuchElementException();
        }
        recalculateCart(cart);
    }

    @Override
    public void delete(Cart cart, Product product) {
        cart.getCartItems().removeIf(cartItem -> product.equals(cartItem.getProduct()));
        recalculateCart(cart);
    }

    private void recalculateCart(Cart cart){
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, (x,y) -> x.add(y));
        cart.setTotalPrice(totalPrice);
    }

    @Override
    public void clearCart(Cart cart) {
        cart.getCartItems().clear();
        recalculateCart(cart);
    }
}
