package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private static final String CART_ATTRIBUTE = "cart";
    private static volatile CartService cartService;

    private CartServiceImpl() {
    }

    public static CartService getInstance() {
        if (cartService == null) {
            synchronized (CartServiceImpl.class) {
                if (cartService == null) {
                    cartService = new CartServiceImpl();
                }
            }
        }
        return cartService;
    }

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void addToCart(Cart mCart, Product product, int quantity) throws NotEnoughStockException {
        Long productId = product.getId();
        Optional<CartItem> cartItemOptional = mCart.getCartItems().stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findAny();
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            if (quantity + cartItem.getQuantity() <= product.getStock()) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            } else {
                throw new NotEnoughStockException("");
            }
        } else {
            if (quantity  <= product.getStock()) {
                mCart.getCartItems().add(new CartItem(product, quantity));
            } else {
                throw new NotEnoughStockException("");
            }
        }
    }
}



