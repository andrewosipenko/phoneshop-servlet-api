package com.es.phoneshop.model;

import com.es.phoneshop.model.order.AbstractOrderItem;

import java.io.Serializable;
import java.util.Objects;

public class CartItem extends AbstractOrderItem implements Serializable{
    public CartItem(Product product, int quantity) {
        super(product, quantity);
    }

    public CartItem() {
        super();
    }
}
