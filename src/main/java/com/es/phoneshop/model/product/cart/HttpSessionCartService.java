package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import java.util.Optional;

public class HttpSessionCartService implements CartService {

    private static HttpSessionCartService INSTANCE;

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
