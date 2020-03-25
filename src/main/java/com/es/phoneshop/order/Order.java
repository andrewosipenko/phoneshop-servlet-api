package com.es.phoneshop.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.item.Item;

import java.math.BigDecimal;
import java.util.List;

public class Order extends Item {
    private BigDecimal deliveryCost;
    private BigDecimal subtotal;
    private BigDecimal totalPrice;
    private String id;
    private OrderCreateForm orderCreateForm;
    private List<CartItem> cartItems;

    public Order(Cart cart) {
        this.setCartItems(cart.getCartItems());
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getSecureId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderCreateForm getOrderCreateForm() {
        return orderCreateForm;
    }

    public void setOrderCreateForm(OrderCreateForm orderCreateForm) {
        this.orderCreateForm = orderCreateForm;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
