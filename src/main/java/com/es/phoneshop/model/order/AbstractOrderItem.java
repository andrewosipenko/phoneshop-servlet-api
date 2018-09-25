package com.es.phoneshop.model.order;

import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;

import java.io.Serializable;
import java.util.Objects;

public class AbstractOrderItem implements Serializable{
    private Product product;
    private int quantity;

    public AbstractOrderItem(){
        this.product = new Product();
        this.quantity = 0;
    }
    public AbstractOrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractOrderItem item = (AbstractOrderItem) o;
        return product.equals(item.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getId(), quantity);
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
}
