package com.es.phoneshop.model;

import com.es.phoneshop.exceptions.StockIsEmptyException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CartService {
    private static volatile CartService instance;
    private static final String CART_ATTRIBUTE_NAME = "cart";
    private ProductDao productDao = ArrayListProductDao.getInstance();

    private CartService() {}

    public static CartService getInstance() {
        CartService localInstance = instance;
        if (localInstance == null) {
            synchronized (CartService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CartService();
                }
            }
        }
        return localInstance;
    }

    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE_NAME);
        if (cart == null) {
            cart = new Cart();
            for (Product product: ArrayListProductDao.getInstance().findProducts()) {
                add(cart, product, product.getStock());
            }
        }
        session.setAttribute(CART_ATTRIBUTE_NAME, cart);
        return cart;
    }

    public void add(Cart cart, Product product, int quantity) throws StockIsEmptyException {
        if (productDao.getProduct(product.getId()).getStock() >= 0)
            cart.getCartItems().add(new CartItem(product, quantity));
        else
            throw new StockIsEmptyException("This Product's stock is empty");
    }
}
