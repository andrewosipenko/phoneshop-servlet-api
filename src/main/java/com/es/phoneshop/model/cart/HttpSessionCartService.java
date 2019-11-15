package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART_ATTRIBUTE = CartService.class + ".cart";
    private static CartService cartService;

    private HttpSessionCartService() {
    }

    public static CartService getInstance() {
        if (cartService == null) {
            synchronized (CartService.class) {
                if (cartService == null) {
                    cartService = new HttpSessionCartService();
                }
            }
        }
        return cartService;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, Product product, int quantity) {
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(cartItem -> product.equals(cartItem.getProduct()))
                .findFirst();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;
            if (newQuantity > product.getStock()) {
                throw new OutOfStockException(product.getStock());
            }
            cartItem.setQuantity(newQuantity);
        } else {
            if (quantity > product.getStock()) {
                throw new OutOfStockException(product.getStock());
            }
            cart.getCartItems().add(new CartItem(product, quantity));
        }
        recalculateTotals(cart);
    }

    @Override
    public void update(Cart cart, Product product, int quantity) {
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(cartItem -> product.equals(cartItem.getProduct()))
                .findFirst();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            int newQuantity = quantity;
            if (newQuantity > product.getStock()) {
                throw new OutOfStockException(product.getStock());
            }
            if (newQuantity == 0) {
                delete(cart, product);
            } else {
                cartItem.setQuantity(newQuantity);
            }
        } else {
            if (quantity > product.getStock()) {
                throw new OutOfStockException(product.getStock());
            }
            cart.getCartItems().add(new CartItem(product, quantity));
        }
        recalculateTotals(cart);
    }

    @Override
    public void delete(Cart cart, Product product) {
        CartItem cartItemToDelete = cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .get();
        cart.getCartItems().remove(cartItemToDelete);
        recalculateTotals(cart);
    }

    private void recalculateTotals(Cart cart) {
        cart.setTotalQuantity((int) cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum());
        cart.setTotalCost(BigDecimal.valueOf(cart.getCartItems().stream()
                .mapToInt(item -> item.getQuantity() * item.getProduct().getPrice().intValueExact())
                .sum()));
    }
}
