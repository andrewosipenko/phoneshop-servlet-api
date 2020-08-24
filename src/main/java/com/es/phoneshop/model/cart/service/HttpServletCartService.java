package com.es.phoneshop.model.cart.service;

import com.es.phoneshop.model.cart.entity.Cart;
import com.es.phoneshop.model.cart.entity.CartItem;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.web.exceptions.OutOfStockException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

public enum HttpServletCartService implements CartService<HttpServletRequest> {
    INSTANCE;

    private static final String CART_SESSION_ATTRIBUTE = HttpServletCartService.class.getName() + ".cart";

    private final ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    public Cart getCart(HttpServletRequest request) {
        synchronized (request.getSession()) {
            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        }
    }

    @Override
    public void add(Cart cart, Long productId, int quantity) throws OutOfStockException {
        //todo refactoring
        synchronized (cart) {
            try {
                Product product = productDao.getItem(productId).get();

                if (quantity > product.getStock()) {
                    throw new OutOfStockException();
                }

                Optional<CartItem> optionalCartItem = findItemInCart(cart, productId);
                if (optionalCartItem.isPresent()) {
                    var cartItem = optionalCartItem.get();
                    if (product.getStock() >= cartItem.getQuantity() + quantity) {
                        increaseQuantity(cartItem, quantity);
                    } else {
                        throw new OutOfStockException();
                    }
                } else {
                    cart.getItems().add(new CartItem(product, quantity));
                }

                recalculateCart(cart);
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException(String.valueOf(productId));
            }
        }
    }


    //todo refactor
    @Override
    public void update(Cart cart, Long productId, int quantity) throws OutOfStockException {
        synchronized (cart) {
            try {
                Product product = productDao.getItem(productId).get();

                if (quantity > product.getStock()) {
                    throw new OutOfStockException();
                }

                Optional<CartItem> optionalCartItem = findItemInCart(cart, productId);
                if (optionalCartItem.isPresent()) {
                    var cartItem = optionalCartItem.get();
                    if (product.getStock() >= cartItem.getQuantity()) {
                        cartItem.setQuantity(quantity);
                    } else {
                        throw new OutOfStockException();
                    }
                }
                recalculateCart(cart);
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException(String.valueOf(productId));
            }
        }
    }

    @Override
    public void delete(Cart cart, Long productID) {
        synchronized (cart) {
            cart.getItems().removeIf(cartItem ->
                    cartItem.getProduct().getId().equals(productID));
            recalculateCart(cart);
        }
    }

    @Override
    public void clearCart(Cart cart) {
        cart.getItems().clear();
        recalculateCart(cart);
    }

    //this method must be called in all public methods, it's a pity that there is no any contract to guarantee it
    private void recalculateCart(Cart cart) {
        recalculateTotalCost(cart);
        recalculateTotalQuantity(cart);
    }

    private void recalculateTotalCost(Cart cart) {
        cart.setTotalCost(cart.getItems()
                .stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO));
    }

    private void recalculateTotalQuantity(Cart cart) {
        cart.setTotalQuantity(cart.getItems()
                .stream()
                .map(CartItem::getQuantity)
                .reduce(Integer::sum)
                .orElse(0));
    }

    private Optional<CartItem> findItemInCart(Cart cart, Long productId) {
        return cart.getItems()
                .stream()
                .filter(existingCartItem -> existingCartItem.getProduct().getId().equals(productId))
                .findAny();
    }

    //unsafe, requires processing and checking out of stock exception case
    private void increaseQuantity(CartItem cartItem, int quantity) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }
}
