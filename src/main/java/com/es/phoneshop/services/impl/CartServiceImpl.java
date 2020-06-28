package com.es.phoneshop.services.impl;

import com.es.phoneshop.exceptions.NotEnoughElementsException;
import com.es.phoneshop.model.*;
import com.es.phoneshop.services.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    private static CartService cartService;
    private final String CART_SESSION = "keyCart";
    ProductDao productDao;

    public CartServiceImpl() {
        this.productDao = ArrayListProductDao.getInstance();
    }

    public static CartService getInstance() {
        return cartService == null ? new CartServiceImpl() : CartServiceImpl.cartService;
    }

    @Override
    public Cart getCart(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        Cart cart = (Cart) httpSession.getAttribute(CART_SESSION);
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute(CART_SESSION, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, Long productId, Long quantity) throws NotEnoughElementsException {
        Product product = productDao.getProduct(productId);
        Optional<CartItem> currentCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst();
        Long currentQuantityOfProductInCart = currentCartItem.isPresent() ? currentCartItem.get().getQuantity() : 0;

        if (quantity + currentQuantityOfProductInCart > product.getStock()) {
            throw new NotEnoughElementsException("shop doesn't have enough amount" + product.getDescription());
        }

        if (currentCartItem.isPresent()) {
            currentCartItem.get().setQuantity(currentCartItem.get().getQuantity() + quantity);
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }

        BigDecimal currentPrice = product.getCurrentPrice().getCost().multiply(BigDecimal.valueOf(quantity));
        cart.setPrice(cart.getPrice().add(currentPrice));
    }
}
