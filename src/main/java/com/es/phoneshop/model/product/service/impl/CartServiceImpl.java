package com.es.phoneshop.model.product.service.impl;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartItem;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.exception.OutOfStockException;
import com.es.phoneshop.model.product.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class.getName() + ".cart";
    private static volatile CartServiceImpl instance;

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
            return cart;
        }
    }

    @Override
    public void add(Cart cart, Long productId, int quantity, HttpServletRequest request) throws OutOfStockException {
        HttpSession currentSession = request.getSession();
        synchronized (currentSession) {
            Product product = productDao.getProduct(productId);
            if (calculateQuantityConsideringCart(cart, product) < quantity) {
                throw new OutOfStockException(product, quantity, product.getStock());
            }
            Optional<CartItem> productMatch = cart.getItems().stream().
                    filter(currProduct -> currProduct.getProduct().equals(product)).
                    findAny();
            if (productMatch.isPresent()) {
                cart.getItems().
                        get(cart.getItems().indexOf(productMatch.get())).
                        setQuantity(productMatch.get().getQuantity() + quantity);
            } else {
                cart.getItems().add(new CartItem(product, quantity));
            }
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
}
