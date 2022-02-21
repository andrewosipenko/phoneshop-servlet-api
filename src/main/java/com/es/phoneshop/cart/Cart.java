package com.es.phoneshop.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Cart  implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private  List<CartItem> items;
    private AtomicInteger totalQuantity;
    private BigDecimal totalCost;

    public Cart() {
        this.items = new CopyOnWriteArrayList<>();
        this.totalQuantity = new AtomicInteger(0);
        this.totalCost = new BigDecimal(0);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Optional<CartItem> getCartItemByProductId(Long id) {
        for (CartItem item : items) {
            if (Objects.equals(item.getProduct().getId(), id)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public int getCurrentQuantityById(Long id) {
        if(getCartItemByProductId(id).isPresent()){
            return getCartItemByProductId(id).get().getQuantity().get();
        }else {
            return 0;
        }
    }

    public void deleteCartItemById(Long id){
        if(getCartItemByProductId(id).isPresent()){
            items.remove(getCartItemByProductId(id).get());
        }
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public AtomicInteger getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(AtomicInteger totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "[" + items + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
