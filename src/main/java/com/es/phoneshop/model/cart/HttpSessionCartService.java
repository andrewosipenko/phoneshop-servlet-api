package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class HttpSessionCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = HttpSessionCartService.class.getName() + ".cart";
    private static HttpSessionCartService instance;
    private final ProductDao productDao = ArrayListProductDao.getInstance();

    public static HttpSessionCartService getInstance() {
        if (instance == null) {
            instance = new HttpSessionCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        synchronized (request.getSession()) {
            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
            if (cart == null) {
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
            }
            return cart;
        }
    }

    @Override
    public void update(HttpServletRequest request, Long productId, int quantity) throws OutOfStockException {
        synchronized (request.getSession()) {
            Cart cart = getCart(request);
            Product product = productDao.getProduct(productId);
            List<CartItem> cartItems = getMatchingCartItem(cart, productId);
            if (cartItems.size() == 1) {
                CartItem cartItem = cartItems.get(0);
                if (quantity > product.getStock()) {
                    throw new OutOfStockException(product.getStock());
                }
                cartItem.setQuantity(quantity);
            }
            calculateTotalCost(cart);
            calculateTotalQuantity(cart);
        }
    }

    @Override
    public void add(HttpServletRequest request, Long productId, int quantity) throws OutOfStockException {
        synchronized (request.getSession()) {
            Cart cart = getCart(request);
            Product product = productDao.getProduct(productId);
            List<CartItem> cartItems = getMatchingCartItem(cart, productId);
            if (cartItems.size() == 1) {
                CartItem cartItem = cartItems.get(0);
                if (cartItem.getQuantity() + quantity > product.getStock()) {
                    throw new OutOfStockException(product.getStock());
                }
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            } else {
                if (quantity > product.getStock()) {
                    throw new OutOfStockException(product.getStock());
                }
                cart.getCartItems().add(new CartItem(product, quantity));
            }
            calculateTotalCost(cart);
            calculateTotalQuantity(cart);
        }
    }

    @Override
    public void delete(HttpServletRequest request, Long productId) {
        synchronized (request.getSession()) {
            Cart cart = getCart(request);
            cart.getCartItems().removeIf(cartItem -> productId != null && productId.equals(cartItem.getProduct().getId()));
            calculateTotalCost(cart);
            calculateTotalQuantity(cart);
        }
    }

    private List<CartItem> getMatchingCartItem(Cart cart, Long productId) {
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct() != null && cartItem.getProduct().getId().equals(productId))
                .collect(Collectors.toList());
    }

    private void calculateTotalCost(Cart cart) {
        BigDecimal totalCost = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalCost(totalCost);
    }

    private void calculateTotalQuantity(Cart cart) {
        int totalQuantity = cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        cart.setTotalQuantity(totalQuantity);
    }
}
