package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultCartService implements CartService{
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";
    private ProductDao productDao;

    private static volatile CartService instance;

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
    }


    public static CartService getInstance() {
        CartService localInstance = instance;
        if (localInstance == null) {
            synchronized (ProductDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DefaultCartService();
                }
            }
        }
        return localInstance;
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
    public synchronized void addProductToCart(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);
        Optional<CartItem> result = findCartItem(cart, productId, quantity);

        if (result.isPresent()) {
            result.get().setQuantity(result.get().getQuantity() + quantity);
            recalculateCart(cart);
            return;
        }

        CartItem cartItem = new CartItem(product, quantity);
        cart.getItems().add(cartItem);
        recalculateCart(cart);
    }

    @Override
    public void updateCart(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);
        Optional<CartItem> result = findCartItem(cart, productId, quantity);

        if (result.isPresent()) {
            result.get().setQuantity(quantity);
            recalculateCart(cart);
            return;
        }
        CartItem cartItem = new CartItem(product, quantity);
        cart.getItems().add(cartItem);
        recalculateCart(cart);
    }

    @Override
    public void deleteProductFromCart(Cart cart, Long productId) {
        cart.getItems().removeIf(cartItem -> productId.equals(cartItem.getProduct().getId()));
        recalculateCart(cart);
    }

    private Optional<CartItem> findCartItem(Cart cart, Long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);
        if (product.getStock() < quantity) {
            throw new OutOfStockException(product, quantity, product.getStock());
        }

        return cart.getItems().stream()
                .filter(cartItem1 -> cartItem1.getProduct().getId().equals(productId))
                .findAny();
    }

    private void recalculateCart(Cart cart){
        cart.setTotalQuantity(cart.getItems().stream()
                        .map(CartItem::getQuantity)
                        .collect(Collectors.summingInt(q -> q.intValue()))
        );

        BigDecimal[] totalCost = {new BigDecimal(0)};
        cart.getItems().forEach(item -> {
            totalCost[0] = totalCost[0].add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        });
        cart.setTotalCost(totalCost[0]);
    }
}
