package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DefaultCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";
    private static CartService instance;
    private static final Object mutex = new Object();
    private final ReadWriteLock rwLock;
    private final ProductDao productDao;

    public static CartService getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new DefaultCartService();
                }
            }
        }
        return instance;
    }

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
        rwLock = new ReentrantReadWriteLock();
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        rwLock.readLock().lock();
        try {
            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public void add(Cart cart, Long productId, int quantity) {
        rwLock.writeLock().lock();
        try {
            Optional<Product> product = productDao.getProduct(productId);
            if (product.isPresent()) {
                CartItem cartItem = new CartItem(product.get(), quantity);
                if (cart.getItems().isEmpty()) {
                    cart.getItems().add(cartItem);
                    return;
                }
                for (CartItem item : cart.getItems()) {
                    if (item.equals(cartItem)) {
                        item.setQuantity(item.getQuantity() + quantity);
                        return;
                    }
                }
                cart.getItems().add(cartItem);
            }
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
