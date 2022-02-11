package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = HttpSessionCartService.class.getName() + ".cart";
    private static final String LOCK_SESSION_ATTRIBUTE = HttpSessionCartService.class.getName() + ".lock";
    private final ProductDao productDao;
    private static HttpSessionCartService instance;

    public static synchronized HttpSessionCartService getInstance() {
        if (instance == null) {
            instance = new HttpSessionCartService();
        }
        return instance;
    }

    private HttpSessionCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        Lock lock = (Lock) request.getSession().getAttribute(LOCK_SESSION_ATTRIBUTE);
        if(lock == null) {
            synchronized (request.getSession()) {
                lock = (Lock) request.getSession().getAttribute(LOCK_SESSION_ATTRIBUTE);
                if (lock == null) {
                    lock = new ReentrantLock();
                    request.getSession().setAttribute(LOCK_SESSION_ATTRIBUTE, lock);
                }
            }
        }
        lock.lock();
        try {
            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                cart = new Cart();
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart);
            }
            return cart;
        } finally {
            lock.unlock();
        }
    }
    @Override
    public void update(Cart cart, Long productId, int quantity, HttpSession session) throws OutOfStockException {
        Lock lock = getLock(session);
        lock.lock();
        try {
            Product p = productDao.getProduct(productId).get();
            if (p.getStock() < quantity) {
                throw new OutOfStockException(p, quantity, p.getStock());
            }
            cart.getItems().put(p, quantity);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void add(Cart cart, Long productId, int quantity, HttpSession session) throws OutOfStockException {
        Lock lock = getLock(session);
        lock.lock();
        try {
            Product p = productDao.getProduct(productId).get();
            if (p.getStock() < quantity + cart.get(p)) {
                throw new OutOfStockException(p, quantity, p.getStock());
            }
            Integer amount = cart.getItems().get(p);
            cart.getItems().put(p, (amount == null ? 0 : amount) + quantity);
        } finally {
            lock.unlock();
        }
    }

    private Lock getLock(HttpSession session) {
        Lock lock = (Lock) session.getAttribute(LOCK_SESSION_ATTRIBUTE);
        synchronized (session) {
            if (lock == null) {
                lock = new ReentrantLock();
                session.setAttribute(LOCK_SESSION_ATTRIBUTE, lock);
            }
        }
        return lock;
    }

}
