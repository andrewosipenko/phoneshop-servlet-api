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

import java.math.BigDecimal;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private ProductDao productDao;
    private final FunctionalReadWriteLock lock;
    private static final String SEPARATE_CART_SESSION_ATTRIBUTE = CartServiceImpl.class.getName() + ".cart";
    private static final String NOT_ENOUGH_STOCK_MESSAGE = "Not enough stock";

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
        Optional<CartItem> cartItem = getCartItemByProductId(productId, cart);
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
        calculateMiniCart(cart);
    }

    @Override
    public void update(Long productId, int quantity, Cart cart) {
        lock.write(() -> {
            Product product = productDao.getProduct(productId);
            Optional<CartItem> cartItem = getCartItemByProductId(productId, cart);
            if (product.getStock() < quantity) {
                throw new OutOfStockException(NOT_ENOUGH_STOCK_MESSAGE);
            }
            if (cartItem.isPresent()) {
                cartItem.get().setQuantity(quantity);
            }
            calculateMiniCart(cart);
        });
    }

    @Override
    public void delete(Long productId, Cart cart) {
        lock.write(() -> {
            cart.getCartItems().removeIf(cartItem ->
                    productId.equals(cartItem.getProduct().getId()));
            calculateMiniCart(cart);
        });
    }

    @Override
    public void clearCart(Cart cart, HttpServletRequest request) {
        lock.write(() -> {
            cart.getCartItems().stream()
                    .forEach(item -> item.getProduct().setStock(item.getProduct().getStock() - item.getQuantity()));
            request.getSession(false).removeAttribute(SEPARATE_CART_SESSION_ATTRIBUTE);
        });
    }

    private void calculateMiniCart(Cart cart) {
        cart.setTotalQuantity(cart.getCartItems().stream()
                .map(cartItem -> cartItem.getQuantity())
                .reduce(0, Integer::sum));
        cart.setTotalCost(cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private Optional<CartItem> getCartItemByProductId(Long productId, Cart cart) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }
}
