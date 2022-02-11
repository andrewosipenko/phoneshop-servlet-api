package com.es.phoneshop.cart;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cart  implements Serializable {
    private final List<CartItem> items;

    public Cart() {
        this.items = new CopyOnWriteArrayList<>();
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
            return getCartItemByProductId(id).get().getQuantity();
        }else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "[" + items + "]";
    }
}
