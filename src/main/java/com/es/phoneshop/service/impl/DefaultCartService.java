package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class DefaultCartService implements CartService {
    private static final String CART_ATTRIBUTE = "cart_" + DefaultCartService.class;
    private ProductDao productDao;

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static DefaultCartService getInstance() {

        return CartServiceHolder.instance;
    }

    private static class CartServiceHolder {
        private static final DefaultCartService instance = new DefaultCartService();
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void add(Cart cart, long productId, int quantity) throws OutOfStockException {
        Product product = productDao.getProduct(productId);
        Optional<CartItem> oneProductItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProductId() == productId)
                .findAny();
        int oneProductQuantity = oneProductItem.map(CartItem::getQuantity).orElse(0);
        int stockAvailable = product.getStock() - oneProductQuantity;
        if (stockAvailable < quantity) {
            throw new OutOfStockException(stockAvailable);
        }
        if (oneProductItem.isPresent()) {
            oneProductItem.get().setQuantity(oneProductQuantity + quantity);
        } else {
            cart.getCartItems().add(new CartItem(productId, quantity));
        }
    }
}
