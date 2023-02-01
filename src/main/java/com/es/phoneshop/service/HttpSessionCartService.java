package com.es.phoneshop.service;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Objects;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = HttpSessionCartService.class.getName() + ".cart";

    private ProductDao productDao;

    private HttpSessionCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    private static CartService instance;

    public static synchronized CartService getInstance() {
        if (instance == null) {
            instance = new HttpSessionCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        synchronized (session) {
            Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);

            if (cart == null) {
                cart = new Cart();
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart);
            }

            return cart;
        }
    }

    @Override
    public void add(Long productId, int quantity, HttpServletRequest request) throws OutOfStockException {
        HttpSession session = request.getSession();

        synchronized (session) {
            Product product = productDao.getProduct(productId);
            Cart cart = getCart(request);

            CartItem cartItem = findCartItemByProduct(cart, product);

            if (cartItem != null) {
                addExistedInCartProduct(product, cartItem, quantity);
            } else {
                addNonExistedInCartProduct(cart, product, quantity);
            }

            recalculateCart(cart);
        }
    }

    @Override
    public void update(Long productId, int quantity, HttpServletRequest request) throws OutOfStockException {
        HttpSession session = request.getSession();

        synchronized (session) {
            Product product = productDao.getProduct(productId);
            Cart cart = getCart(request);

            CartItem cartItem = findCartItemByProduct(cart, product);

            if (cartItem != null) {
                checkIfQuantityGreaterThanStock(product, quantity);
                cartItem.setQuantity(quantity);
            } else {
                throw new ProductNotFoundException(productId);
            }

            recalculateCart(cart);
        }
    }

    @Override
    public void delete(Long productId, HttpServletRequest request) {
        HttpSession session = request.getSession();

        synchronized (session) {
            Cart cart = getCart(request);
            cart.getItems().removeIf(item -> Objects.equals(item.getProduct().getId(), productId));
            recalculateCart(cart);
        }
    }

    private void addExistedInCartProduct(Product product, CartItem cartItem, int quantity) throws OutOfStockException {
        quantity += cartItem.getQuantity();
        checkIfQuantityGreaterThanStock(product, quantity);
        cartItem.setQuantity(quantity);
    }

    private void addNonExistedInCartProduct(Cart cart, Product product, int quantity) throws OutOfStockException {
        checkIfQuantityGreaterThanStock(product, quantity);
        cart.getItems().add(new CartItem(product, quantity));
    }

    private void checkIfQuantityGreaterThanStock(Product product, int quantity) throws OutOfStockException {
        if (product.getStock() < quantity) {
            throw new OutOfStockException(product, quantity, product.getStock());
        }
    }

    private void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity)
                .mapToInt(q -> q).sum());
        cart.setTotalCost(cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private CartItem findCartItemByProduct(Cart cart, Product product) {
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().equals(product))
                .findAny()
                .orElse(null);
    }
}
