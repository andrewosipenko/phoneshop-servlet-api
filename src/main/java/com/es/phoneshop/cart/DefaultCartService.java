package com.es.phoneshop.cart;

import com.es.phoneshop.lock.SessionLock;
import com.es.phoneshop.lock.SessionLockService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class DefaultCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";
    private static CartService instance;
    private static final Object mutex = new Object();
    private final ProductDao productDao;
    private final SessionLock lock;

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
        lock = SessionLockService.getInstance();
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        lock.getSessionLock(request).lock();
        try {
            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        } finally {
            lock.getSessionLock(request).unlock();
        }
    }

    @Override
    public void add(HttpServletRequest request, Cart cart, Long productId, int quantity) {
        lock.getSessionLock(request).lock();
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
            lock.getSessionLock(request).unlock();
        }
    }

    @Override
    public boolean isEnoughStockForOrder(HttpServletRequest request, Product product, int quantity) {
        lock.getSessionLock(request).lock();
        try {
            if (!productDao.getProduct(product.getId()).isPresent()) {
                return false;
            }
            int currentAmount = getCart(request).getCurrentQuantityById(product.getId());
            return quantity + currentAmount <= product.getStock();
        }finally {
            lock.getSessionLock(request).unlock();
        }
    }
}
