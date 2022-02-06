package com.es.phoneshop.model.cart.cartService.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.cartService.CartService;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DefaultCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";

    private final ProductDao productDao;

    private final ReadWriteLock locker;

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
        locker = new ReentrantReadWriteLock();
    }

    private static final class DefaultCartServiceHolder {
        private static final DefaultCartService INSTANCE = new DefaultCartService();
    }

    public static DefaultCartService getInstance() {
        return DefaultCartServiceHolder.INSTANCE;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        locker.readLock().lock();
        try {
            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        } finally {
        locker.readLock().unlock();
    }
    }

    @Override
    public void add(Cart cart, Long productId, int quantity) throws OutOfStockException {
        locker.writeLock().lock();
        try {
            Product product = productDao.getProduct(productId);

            Optional<CartItem> cartItem = cart.getItems().stream()
                    .filter(item -> productId.equals(item.getProduct().getId()))
                    .findAny();

            int quantityInCart = cartItem.map(CartItem::getQuantity).orElse(0);

            int availableStock = product.getStock() - quantityInCart;

            if (availableStock - quantity < 0) {
                throw new OutOfStockException(product, quantity, availableStock);
            }

            if (cartItem.isPresent()) {
                cartItem.get().increaseQuantity(quantity);
            } else {
                cart.getItems().add(new CartItem(product, quantity));
            }
        } finally {
            locker.writeLock().unlock();
        }
    }

    @Override
    public void addToRecentlyViewed(Cart cart, Product product, int numberOfDisplayedProducts) {
        if (product != null) {
            cart.getRecentlyViewedProducts().removeIf(item -> product.getId().equals(item.getId()));

            if (cart.getRecentlyViewedProducts().size() == numberOfDisplayedProducts) {
                cart.getRecentlyViewedProducts().remove(numberOfDisplayedProducts - 1);
            }

            cart.getRecentlyViewedProducts().add(0, product);
        }
    }
}
