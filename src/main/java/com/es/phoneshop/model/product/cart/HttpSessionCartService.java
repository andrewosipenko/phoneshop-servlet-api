package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.dao.product.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class HttpSessionCartService implements CartService {

    protected static final String HTTP_SESSION_CART_KEY = "httpCart";
    private static HttpSessionCartService instance;

    private HttpSessionCartService() {
    }

    public static HttpSessionCartService getInstance() {
        if (instance == null) {
            synchronized (HttpSessionCartService.class) {
                if (instance == null) {
                    instance = new HttpSessionCartService();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(HttpServletRequest request, Cart customerCart, CartItem newItem) throws ProductNotFoundException, OutOfStockException {
        Product product = ArrayListProductDao.getInstance().getProduct(newItem.getProduct().getId());
        if (newItem.getQuantity() > product.getStock()) {
            throw new OutOfStockException("Not enough stock!");
        }
        Optional<CartItem> itemOptional = customerCart.getCartItems().stream()
                .filter(item -> newItem.getProduct().getId().equals(item.getProduct().getId()))
                .findAny();

        if (itemOptional.isPresent()) {
            CartItem sameCartItem = itemOptional.get();
            if (sameCartItem.getQuantity() + newItem.getQuantity() > product.getStock()) {
                throw new OutOfStockException("Not enough stock!");
            }
            sameCartItem.setQuantity(sameCartItem.getQuantity() + newItem.getQuantity());
        } else {
            customerCart.getCartItems().add(new CartItem(product, newItem.getQuantity()));
        }
        save(request);
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        Cart cart;
        HttpSession session = request.getSession();
        if (session.getAttribute(HTTP_SESSION_CART_KEY) == null) {
            cart = new Cart();
            session.setAttribute(HTTP_SESSION_CART_KEY, cart);
        } else {
            cart = (Cart) session.getAttribute(HTTP_SESSION_CART_KEY);
        }
        return cart;
    }

    @Override
    public void clearCart(HttpServletRequest request) {
        Cart cart = getCart(request);
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
    }

    @Override
    public void save(HttpServletRequest request) {
        Cart cart = getCart(request);
        recalculate(cart);
        request.getSession().setAttribute(HTTP_SESSION_CART_KEY, cart);
    }

    @Override
    public void recalculate(Cart customerCart) {
        List<CartItem> cartItems = customerCart.getCartItems();
        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        customerCart.setTotalPrice(totalPrice);
    }

    @Override
    public boolean remove(Cart customerCart, Long idToRemove) {
        List<CartItem> cartItems = customerCart.getCartItems();
        if (cartItems.removeIf(cartItem -> cartItem.getProduct().getId().equals(idToRemove))) {
            recalculate(customerCart);
            return true;
        }
        return false;
    }


    public void update(Cart cart, Long id, Integer quantity) throws OutOfStockException {
        Optional<CartItem> itemToUpdate = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(id))
                .findFirst();
        if (itemToUpdate.isPresent()) {
            CartItem item = itemToUpdate.get();
            if (item.getProduct().getStock() < quantity) {
                throw new OutOfStockException("Product{id="
                        + id + "} has stock="
                        + item.getProduct().getStock()
                        + ", but wanted=" + quantity);
            } else {
                item.setQuantity(quantity);
            }
        }
    }
}
