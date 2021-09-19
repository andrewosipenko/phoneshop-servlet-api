package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.exceptions.StockException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DefaultCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";

    private ProductDao productDao;
    private static volatile DefaultCartService instance;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static DefaultCartService getInstance() {
        DefaultCartService cart = instance;
        if (cart != null) {
            return cart;
        }
        synchronized (DefaultCartService.class) {
            if (instance == null) {
                instance = new DefaultCartService();
            }
            return instance;
        }
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        lock.writeLock().lock();
        HttpSession session = request.getSession();
        Cart userCart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        lock.writeLock().unlock();
        if (userCart == null) {
            lock.writeLock().lock();
            session.setAttribute(CART_SESSION_ATTRIBUTE, userCart = new Cart());
            lock.writeLock().unlock();
        }
        return userCart;
    }

    @Override
    public synchronized void add(Cart cart, Long productId, int quantity) throws StockException {
        Product product = productDao.getProduct(productId);
        if (product.getStock() < quantity + getQuantityOfCartItem(cart, product)) {
            throw new StockException("Not enough stock");
        }
        if (isProductInCart(cart, product)) {
            addQuantityToCartItem(cart, product, quantity);
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }
    }

    private int getQuantityOfCartItem(Cart cart, Product product) {
        lock.readLock().lock();
        CartItem searchedCartItem = getCartItemByProduct(cart, product);
        int quantity;
        if (searchedCartItem != null) {
            quantity = searchedCartItem.getQuantity();
        } else {
            quantity = 0;
        }
        lock.readLock().unlock();
        return quantity;
    }

    private void addQuantityToCartItem(Cart cart, Product product, int additionalQuantity) {
        lock.writeLock().lock();
        getCartItemByProduct(cart, product).addQuantity(additionalQuantity);
        lock.writeLock().unlock();
    }

    private CartItem getCartItemByProduct(Cart cart, Product product) {
        lock.readLock().lock();
        try {
            return cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getCartProduct().equals(product))
                    .findFirst()
                    .orElse(null);
        } finally {
            lock.readLock().unlock();
        }
    }

    private boolean isProductInCart(Cart cart, Product product) {
        lock.readLock().lock();
        try {
            return cart.getCartItems().stream().anyMatch(cartItem -> cartItem.getCartProduct().equals(product));
        } finally {
            lock.readLock().unlock();
        }
    }
}
