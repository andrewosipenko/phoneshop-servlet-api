package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exceptions.NotEnoughElementsException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.services.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    public static final String CART_SESSION = "keyCart";
    private ProductDao productDao;

    private CartServiceImpl() {
        this.productDao = ArrayListProductDao.getInstance();
    }

    public static CartService getInstance() {
        return CartServiceHolder.instance;
    }

    private static class CartServiceHolder {
        private static final CartService instance = new CartServiceImpl();
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
    public void clearCart(Cart cart) {
        cart.setCartItems(new ArrayList<>());
        cart.setPrice(BigDecimal.ZERO);
    }

    @Override
    public void add(Cart cart, Long productId, Long quantity) throws NotEnoughElementsException {
        Product product = productDao.getById(productId);
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

    @Override
    public void update(Cart cart, Long productId, Long quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be more then 0!");
        }
        Product product = productDao.getById(productId);

        Optional<CartItem> cartItem = cart.getCartItems().stream()
                .filter(cartItem1 -> cartItem1.getProduct() == product).findFirst();

        if (product.getStock() < quantity)
            throw new NotEnoughElementsException("Shop have just "
                    + product.getStock() + " " + product.getDescription());

        if (cartItem.isPresent()) {
            cartItem.get().setQuantity(quantity);
            recalculateCartPrice(cart);
        }
    }

    @Override
    public void updateWithoutChangesProducts(Cart cart) throws IllegalArgumentException, NotEnoughElementsException {
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.stream().forEach(cartItem -> update(cart, cartItem.getProduct().getId(), cartItem.getQuantity()));
    }

    @Override
    public void delete(Cart cart, Long productId) {
        cart.getCartItems().removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
    }

    public void recalculateCartPrice(Cart cart) {
        cart.setPrice(cart.getCartItems()
                .stream()
                .map(cartItem -> cartItem.getProduct().getCurrentPrice().getCost()
                        .multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
