package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.productdao.Product;
import com.es.phoneshop.model.product.exceptions.DeleteException;
import com.es.phoneshop.model.product.exceptions.QuantityLowerZeroException;
import com.es.phoneshop.model.product.exceptions.StockException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    Cart getCart(HttpServletRequest request);

    void addToCart(Cart cart, Long productId, int quantity) throws StockException;

    void putToCart(Cart cart, Long productId, int quantity) throws StockException, QuantityLowerZeroException;

    void updateCart(Cart cart);

    void deleteFromCart(Cart cart, Long productId) throws DeleteException;

    int getQuantityOfCartItem(Cart cart, Product product);
}
