package com.es.phoneshop.model.product;

import java.math.BigDecimal;

public class ProductValidator {
    public static boolean validateProduct(Product product) {
        boolean isValid = validateId(product);
        isValid &= validateCode(product);
        isValid &= validateDescription(product);
        isValid &= validatePrice(product);
        isValid &= validateStock(product);
        isValid &= validateImageUrl(product);
        return isValid;
    }

    public static boolean validateId(Product product) {
        if (product.getId() != null) {
            return product.getId() > 0;
        }
        return true;
    }

    public static boolean validateCode(Product product) {
        return validateString(product.getCode());
    }

    public static boolean validateDescription(Product product) {
        return validateString(product.getDescription());
    }

    public static boolean validatePrice(Product product) {
        if (product.getPrice() != null) {
            return product.getPrice().compareTo(BigDecimal.ZERO) >= 0;
        }
        return true;
    }

    public static boolean validateStock(Product product) {
        return product.getStock() >= 0;
    }

    public static boolean validateImageUrl(Product product) {
        return validateString(product.getImageUrl());
    }

    private static boolean validateString(String string) {
        if (string != null) {
            return string.length() > 0;
        }
        return true;
    }
}
