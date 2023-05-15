package com.es.phoneshop.service;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class CartServiceImpl implements CartService {

    static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class.getName() + ".cart";
    private static CartServiceImpl instance;
    private ProductService productService;

    private CartServiceImpl() {
        productService = ProductServiceImpl.getInstance();
    }

    public static synchronized CartServiceImpl getInstance() {
        if (instance == null) {
            instance = new CartServiceImpl();
        }
        return instance;
    }

    @Override
    public synchronized Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
        }
        return cart;
    }

    @Override
    public synchronized void add(Cart cart, long productId, int quantity) throws OutOfStockException {
        Product product = productService.getProduct(productId);
        Optional<CartItem> existingCartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().equals(product))
                .findAny();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            if (product.getStock() < quantity + cartItem.getQuantity()) {
                throw new OutOfStockException(product, quantity, product.getStock() - cartItem.getQuantity());
            }
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            if (product.getStock() < quantity) {
                throw new OutOfStockException(product, quantity, product.getStock());
            }
            cart.getItems().add(new CartItem(product, quantity));
        }
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
