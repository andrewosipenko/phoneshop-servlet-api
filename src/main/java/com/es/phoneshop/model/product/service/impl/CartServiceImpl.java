package com.es.phoneshop.model.product.service.impl;

import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.entity.Cart;
import com.es.phoneshop.model.product.entity.CartItem;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.exception.OutOfStockException;
import com.es.phoneshop.model.product.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class.getName() + ".cart";
    private static volatile CartServiceImpl instance;

    private String POSSIBLE_ERROR_MESSAGE = "No such product with given code";

    private ProductDao productDao;

    public static CartServiceImpl getInstance() {
        CartServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (CartServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new CartServiceImpl();
            }
        }
        return localInstance;
    }

    private CartServiceImpl() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession currentSession = request.getSession();
        synchronized (currentSession) {
            Cart cart = (Cart) currentSession.getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                cart = new Cart();
                currentSession.setAttribute(CART_SESSION_ATTRIBUTE, cart);
            }
            if (cart.getTotalCost() == null) {
                cart.setTotalCost(BigDecimal.ZERO);
            }
            return cart;
        }
    }

    @Override
    public void add(Cart cart, Long productId, int quantity, HttpServletRequest request) throws OutOfStockException {
        HttpSession currentSession = request.getSession();
        Optional<CartItem> productMatch;
        synchronized (currentSession) {
            Product product = productDao.getEntity(productId, POSSIBLE_ERROR_MESSAGE);
            if (calculateQuantityConsideringCart(cart, product) < quantity) {
                throw new OutOfStockException(product, quantity, product.getStock());
            }
            if ((productMatch = getCartItemMatch(cart, product)).isPresent()) {
                cart.getItems().
                        get(cart.getItems().indexOf(productMatch.get())).
                        setQuantity(productMatch.get().getQuantity() + quantity);
            } else {
                cart.getItems().add(new CartItem(product, quantity));
                currentSession.setAttribute("cart", cart);
            }
            reCalculateCart(cart);
        }
    }

    @Override
    public void update(Cart cart, Long productId, int quantity, HttpServletRequest request) throws OutOfStockException {
        HttpSession currentSession = request.getSession();
        synchronized (currentSession) {
            Product product = productDao.getEntity(productId, POSSIBLE_ERROR_MESSAGE);
            if (quantity > product.getStock()) {
                throw new OutOfStockException(product, quantity, product.getStock());
            }
            getCartItemMatch(cart, product).ifPresent(cartItem -> cart.getItems().
                    get(cart.getItems().indexOf(cartItem)).
                    setQuantity(quantity));
            reCalculateCart(cart);
        }
    }

    @Override
    public void delete(Cart cart, Long productId, HttpServletRequest request) {
        HttpSession currentSession = request.getSession();
        synchronized (currentSession) {
            Product product = productDao.getEntity(productId, POSSIBLE_ERROR_MESSAGE);
            cart.getItems().removeIf(item -> productId.equals(item.getProduct().getId()));
            reCalculateCart(cart);
        }
    }

    private int calculateQuantityConsideringCart(Cart cart, Product product) {
        int result = product.getStock();
        Optional<Integer> quantityInCart = cart.getItems().stream().
                filter(currProduct -> currProduct.getProduct().equals(product)).
                map(CartItem::getQuantity).
                findAny();
        if (quantityInCart.isPresent()) {
            result -= quantityInCart.get();
        }
        return result;
    }

    @Override
    public void reCalculateCart(Cart cartToRecalculate) {
        BigDecimal totalCost = BigDecimal.ZERO;
        cartToRecalculate.setTotalItems(
                cartToRecalculate.getItems().stream().
                        map(CartItem::getQuantity).
                        mapToInt(q -> q).
                        sum()
        );
        for (CartItem item : cartToRecalculate.getItems()) {
            totalCost = totalCost.add(
                    item.getProduct().getPrice().
                            multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        cartToRecalculate.setTotalCost(totalCost);
    }

    private Optional<CartItem> getCartItemMatch(Cart cart, Product product) {
        return cart.getItems().stream().
                filter(currProduct -> currProduct.getProduct().getId().equals(product.getId())).
                findAny();
    }
}
