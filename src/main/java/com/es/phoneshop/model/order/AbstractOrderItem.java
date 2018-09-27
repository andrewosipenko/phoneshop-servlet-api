package com.es.phoneshop.model.order;

import com.es.phoneshop.model.Product;

import java.io.Serializable;
import java.util.Objects;

public class AbstractOrderItem implements Serializable{
    private Product product;
    private int quantity;

    public AbstractOrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractOrderItem that = (AbstractOrderItem) o;
        return Objects.equals(product, that.product);
    }
    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}