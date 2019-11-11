package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

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
        if (quantity > product.getStock()) {
            throw new OutOfStockException(product.getStock());
        }
        cart.getCartItems().add(new CartItem(product, quantity));
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
