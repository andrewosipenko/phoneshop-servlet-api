package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.bean.CartItem;
import com.es.phoneshop.model.product.bean.Product;

import java.util.List;

public class QuantityValidator {
    private final static String NUMBER_PATTERN = "[0-9]+";

    public static boolean isNumber(String quantity) {
        return quantity.matches(NUMBER_PATTERN);
    }

    public static boolean isEnoughStock(int quantity, Product product, List<CartItem> cartItemList) {
        for (CartItem item : cartItemList) {
            if (item.getProductId() == product.getId()) {
                return (product.getStock() - item.getQuantity()) >= quantity;
            }
        }
        return product.getStock() >= quantity;
    }
}
