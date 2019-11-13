package com.es.phoneshop.model.cart;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HttpSessionCartService implements CartService {
    private static CartService cartService;
    private static Lock lock = new ReentrantLock();

    public static HttpSessionCartService getInstance() {
        lock.lock();
        try {
            if (cartService == null) {
                cartService = new HttpSessionCartService();
            }
            return (HttpSessionCartService) cartService;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    public void addProduct(Cart cart, Product product, int quantity) {
        if (quantity > product.getStock()) {
            throw new OutOfStockException("Not enough product");
        }

        Optional<CartItem> cartItem = cart.findProduct(product);

        if (cartItem.isPresent()) {
            int tempQuantity = cartItem.get().getQuantity();

            if (tempQuantity + quantity > product.getStock()) {
                throw new OutOfStockException("Not enough product");
            } else {
                cartItem.get().setQuantity(tempQuantity + quantity);
            }
        } else {
            cart.getCartItemList().add(new CartItem(product, quantity));
        }

        //product.setStock(product.getStock() - quantity);
        cart.recalculate();
    }
}
