package com.es.phoneshop.service.impl;

import com.es.phoneshop.FunctionalReadWriteLock;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.util.WebUtils;

import java.util.Optional;

public class CartServiceImpl implements CartService {
    private ProductDao productDao;
    private final FunctionalReadWriteLock lock;
    private static final String SEPARATE_CART_SESSION_ATTRIBUTE = CartServiceImpl.class.getName() + ".cart";

    private CartServiceImpl() {
        this.productDao = ArrayListProductDao.getInstance();
        this.lock = new FunctionalReadWriteLock();
    }

    public static CartServiceImpl getInstance() {
        return CartServiceImpl.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final CartServiceImpl INSTANCE = new CartServiceImpl();
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object mutex = WebUtils.getSessionMutex(session);
            synchronized (mutex) {
                Cart cart = (Cart) session.getAttribute(SEPARATE_CART_SESSION_ATTRIBUTE);
                if (Optional.ofNullable(cart).isEmpty()) {
                    session.setAttribute(SEPARATE_CART_SESSION_ATTRIBUTE, cart = new Cart());
                }
                return cart;
            }
        }
        return new Cart();
    }

    @Override
    public synchronized void add(Long productId, int quantity, Cart cart) {
        Product product = productDao.getProduct(productId);
        Optional<CartItem> cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if (product.getStock() < quantity || (cartItem.isPresent()
                && cartItem.get().getQuantity() + quantity > product.getStock())) {
            throw new OutOfStockException("Not enough stock");
        }
        cartItem.ifPresentOrElse(
                (value) -> {
                    cartItem.get().setQuantity(cartItem.get().getQuantity() + quantity);
                },
                () -> {
                    cart.getCartItems().add(new CartItem(product, quantity));
                }
        );
    }
}
