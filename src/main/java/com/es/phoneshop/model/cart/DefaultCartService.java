package com.es.phoneshop.model.cart;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public class DefaultCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";

    private ProductDao productDao;
    private static DefaultCartService instance;

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static DefaultCartService getInstance() {
        if (instance == null) {
            synchronized (DefaultCartService.class) {
                if (instance == null) {
                    instance = new DefaultCartService();
                }
            }
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
    public synchronized void add(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        if (product.getStock() > quantity) {
            throw new OutOfStockException(product, quantity, product.getStock());
        }

        if (cart.getItems().contains(product)) {
            cart.getItems().forEach(item -> {
                        if (item.getProduct() == product) {
                            item.setQuantity(item.getQuantity() + quantity);
                        }
                    });
        } else {
            cart.getItems().add(new CartItem(product, quantity));
        }
    }
}
