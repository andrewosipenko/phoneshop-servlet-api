package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionCartService implements CartService {

    public static final String HTTP_SESSION_CART_KEY = "httpCart";
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
        save(customerCart, request);
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

    private void save(Cart customerCart, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(HTTP_SESSION_CART_KEY, customerCart);
    }
}
