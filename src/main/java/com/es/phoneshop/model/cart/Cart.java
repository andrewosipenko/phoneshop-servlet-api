package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private Set<CartItem> cartItemList;
    private int totalQuantity;
    private BigDecimal totalPrice;

    Cart() {
        cartItemList = new HashSet<>();
        totalPrice = new BigDecimal(0);
    }

    public Cart(Set<CartItem> cartItemList, int totalQuantity, BigDecimal totalPrice) {
        this.cartItemList = cartItemList;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    Set<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(Set<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    void recalculate() {
        int tempTotalQuantity = 0;
        BigDecimal tempTotalPrice = new BigDecimal(0);

        for (CartItem cartItem : cartItemList) {
            tempTotalQuantity += cartItem.getQuantity();
            tempTotalPrice = tempTotalPrice.add(cartItem.getProduct().getPrice()
                    .multiply(new BigDecimal(cartItem.getQuantity())));
        }

        totalQuantity = tempTotalQuantity;
        totalPrice = tempTotalPrice;
    }

    Optional<CartItem> findProduct(Product product) {
        return cartItemList.stream().filter(cartItem -> cartItem.getProduct().equals(product)).findAny();
    }
}
