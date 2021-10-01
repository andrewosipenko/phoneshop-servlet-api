package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.productdao.ArrayListProductDao;
import com.es.phoneshop.model.product.productdao.Product;
import com.es.phoneshop.model.product.productdao.ProductDao;
import com.es.phoneshop.model.product.exceptions.DeleteException;
import com.es.phoneshop.model.product.exceptions.QuantityLowerZeroException;
import com.es.phoneshop.model.product.exceptions.StockException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
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
    public synchronized void addToCart(Cart cart, Long productId, int quantity) throws StockException {
        Product product = productDao.getProduct(productId);
        if (product.getStock() < quantity + getQuantityOfCartItem(cart, product)) {
            throw new StockException("Not enough stock");
        }
        if (isProductInCart(cart, product)) {
            addQuantityToCartItem(cart, product, quantity);
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }
        updateCart(cart);
    }

    @Override
    public synchronized void deleteFromCart(Cart cart, Long productId) throws DeleteException {
        Product product = productDao.getProduct(productId);
        List<CartItem> cartItemList = cart.getCartItems();
        CartItem deleteCartItem = getCartItemByProduct(cart, product);
        if (deleteCartItem != null) {
            cartItemList.remove(deleteCartItem);
        } else {
            throw new DeleteException("No such element to delete");
        }
        updateCart(cart);
    }

    @Override
    public void putToCart(Cart cart, Long productId, int quantity) throws StockException, QuantityLowerZeroException {
        if (quantity <= 0) {
            throw new QuantityLowerZeroException("Not enough stock, available " + productDao.getProduct(productId).getStock());
        }
        Product product = productDao.getProduct(productId);
        int productStock = product.getStock();
        if (productStock < quantity) {
            throw new StockException("Not enough stock, available " + productStock);
        }
        if (isProductInCart(cart, product)) {
            getCartItemByProduct(cart, product).setQuantity(quantity);
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }
        updateCart(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        int sumQuantity = cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity).sum();
        cart.setTotalQuantity(sumQuantity);
        BigDecimal sumPrice = BigDecimal.ZERO;
        for (CartItem item : cart.getCartItems()) {
            sumPrice = sumPrice.add(item.getCartProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        cart.setTotalPrice(sumPrice);
    }

    @Override
    public int getQuantityOfCartItem(Cart cart, Product product) {
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
        updateCart(cart);
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

    @Override
    public void clear(Cart cart) {
        cart.getCartItems().clear();
        cart.setTotalQuantity(0);
        cart.setTotalPrice(BigDecimal.ZERO);
    }
}