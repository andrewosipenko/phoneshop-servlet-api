package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.bean.Cart;
import com.es.phoneshop.model.product.bean.CartItem;
import com.es.phoneshop.model.product.bean.Product;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

public class CartServiceImpl implements CartService {
    private static final String NOT_NUMBER = "Not a number";
    private static final String OUT_OF_STOCK = "Out of stock, available ";
    private static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class.getName() + ".cart";
    private static volatile CartService instance;

    public static CartService getInstance() {
        CartService result = instance;
        if (result != null) {
            return result;
        }
        synchronized (CartService.class) {
            if (instance == null) {
                instance = new CartServiceImpl();
            }
            return instance;
        }
    }

    private CartServiceImpl() {
    }

    @Override
    public synchronized Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public synchronized void add(Cart cart, Long productId, String quantityString, NumberFormat format)
            throws QuantityException {
        int quantity;
        try {
            quantity = format.parse(quantityString).intValue();
        } catch (ParseException ex) {
            throw new QuantityException(NOT_NUMBER);
        }
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        List<CartItem> cartItemList = cart.getItems();
        if (!QuantityValidator.isEnoughStock(quantity, product, cartItemList)) {
            throw new QuantityException(OUT_OF_STOCK + product.getStock());
        }
        boolean existId = false;
        for (CartItem item : cartItemList) {
            if (item.getProductId() == productId) {
                item.addQuantity(quantity);
                existId = true;
            }
        }
        if (!existId) {
            cartItemList.add(new CartItem(productId, quantity));
        }
    }
}
