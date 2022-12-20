package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.NegativeQuantityException;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.exceptions.ZeroQuantityException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultCartService implements CartService {
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";

    private ProductDao productDao;

    public DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    private static class SingletonHelper {
        private static final DefaultCartService INSTANCE = new DefaultCartService();
    }

    public static DefaultCartService getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public synchronized Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            Cart blankCart = new Cart();
            session.setAttribute(CART_SESSION_ATTRIBUTE, blankCart);
        }
        return cart;
    }

    @Override
    public synchronized void add(Cart cart, Long productId, int quantity) throws OutOfStockException, NegativeQuantityException, ZeroQuantityException {
        Product product = productDao.getProduct(productId);

        checkForStock(cart, product, quantity);
        cart.getItems().add(new CartItem(product, quantity));
        recalculateCart(cart);
    }

    @Override
    public synchronized void update(Cart cart, Long productId, int quantity) throws OutOfStockException, NegativeQuantityException
            , ZeroQuantityException {

        Product product = productDao.getProduct(productId);
        Optional<CartItem> cartItemOptional = cart.getItems().stream()
                .filter(c -> product.getId().equals(c.getProduct().getId()))
                .findAny();

        checkForStock(cart, product, quantity);

        if (cartItemOptional.isPresent()) {
            cartItemOptional.get().setQuantity(quantity);
        } else {
            cart.getItems().add(new CartItem(product, quantity));
        }
        recalculateCart(cart);
    }

    @Override
    public synchronized void delete(Cart cart, Long productId) {
        cart.getItems().removeIf(item ->
                productId.equals(item.getProduct().getId())
        );
        recalculateCart(cart);
    }

    @Override
    public void clearCart(Cart cart) {
        cart.setTotalQuantity(0);
        cart.setTotalCost(new BigDecimal(0));
        cart.getItems().clear();
    }

    public void checkForStock(Cart cart, Product product, int quantity) throws OutOfStockException, NegativeQuantityException
            , ZeroQuantityException {
        Optional<CartItem> item = cart.getCartItemByName(product);
        if (item.isPresent()) {
            if(quantity == 0){
                throw new ZeroQuantityException();
            }else if (quantity < 0) {
                throw new NegativeQuantityException();
            } else if (product.getStock() < quantity) {
                throw new OutOfStockException();
            }
        }
    }

    private void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity)
                .collect(Collectors.summingInt(q -> q.intValue())));
        cart.setTotalCost(cart.getItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
