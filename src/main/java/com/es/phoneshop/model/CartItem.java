package com.es.phoneshop.model;

import com.es.phoneshop.model.order.AbstractOrderItem;

import java.io.Serializable;
import java.util.Objects;

public class CartItem extends AbstractOrderItem implements Serializable {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        super(product, quantity);
    }


}
