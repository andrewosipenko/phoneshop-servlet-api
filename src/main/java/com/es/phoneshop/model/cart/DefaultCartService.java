package com.es.phoneshop.model.cart;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class DefaultCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";

    private ProductDao productDao;
    public static volatile DefaultCartService instance;

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
    public synchronized Cart getCart(@NonNull final HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart = new Cart());
        }
        return cart;
    }

    @Override
    public synchronized void add(@NonNull final Cart cart, @NonNull final Long productId, final int quantity) throws OutOfStockException {
        if (productId < 0) {
            throw new IllegalArgumentException("Incorrect product id");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Incorrect quantity of products");
        }

        Product product = productDao.getProduct(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Optional<CartItem> cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findAny();

        final int currentQuantity;
        currentQuantity = cartItem.map(CartItem::getQuantity).orElse(0);

        if (product.getStock() < quantity + currentQuantity) {
            throw new OutOfStockException(product, quantity + currentQuantity, product.getStock());
        }

        if (cartItem.isPresent()) {
            cart.getItems().forEach(item -> {
                if (item.getProduct().getId().equals(product.getId())) {
                    item.setQuantity(currentQuantity + quantity);
                }
            });
        } else {
            cart.getItems().add(new CartItem(product, quantity));
        }
    }
}
