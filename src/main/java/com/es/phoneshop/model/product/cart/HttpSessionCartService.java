package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionCartService implements CartService {

    private static HttpSessionCartService INSTANCE;
    protected static final String HTTP_SESSION_CART_KEY = "httpCart";


    @Override
    public void add(Cart customerCart, Long productId, Integer quantity) throws ProductNotFoundException, OutOfStockException {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        if (quantity > product.getStock()) {
            throw new OutOfStockException("Not enough stock!");
        }
        Optional<CartItem> itemOptional = customerCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findAny();

        if (itemOptional.isPresent()) {
            CartItem sameCartItem = itemOptional.get();
            sameCartItem.setQuantity(sameCartItem.getQuantity() + quantity);
        } else {
            customerCart.getCartItems().add(new CartItem(product, quantity));
        }

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

    public static HttpSessionCartService getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpSessionCartService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpSessionCartService();
                }
            }
        }
        return INSTANCE;
    }

}
