package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.exceptions.QuantityLowerZeroException;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.text.ParseException;

public class WebHelperService {
    private static volatile WebHelperService instance;

    private WebHelperService() {
    }

    public static WebHelperService getInstance() {
        WebHelperService helper = instance;
        if (helper != null) {
            return helper;
        }
        synchronized (DefaultCartService.class) {
            if (instance == null) {
                instance = new WebHelperService();
            }
            return instance;
        }
    }

    public int parseRightQuantity(HttpServletRequest request, String quantityString)
            throws NumberFormatException, QuantityLowerZeroException, ParseException {
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        int quantity = getQuantity(quantityString, format);
        if (quantity <= 0) {
            throw new QuantityLowerZeroException("Quantity should be > 0");
        }
        return quantity;
    }

    private int getQuantity(String quantityString, NumberFormat format) throws ParseException {
        int quantity = format.parse(quantityString).intValue();
        double quantityDouble = format.parse(quantityString).doubleValue();
        if (quantityDouble % 1 != 0) {
            throw new NumberFormatException("Quantity should be integer");
        }
        return quantity;
    }

    public Long parseId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }
}