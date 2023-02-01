package com.es.phoneshop.util;

import java.text.NumberFormat;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Locale;

public class CartItemQuantityValidationUtil {
    public static String validateQuantity(HttpServletRequest request, String quantityString) {
        Number parsedQuantity = parseQuantity(request, quantityString);
        if (parsedQuantity == null)
        {
            return "Not a number";
        }
        Double doubleQuantity = parsedQuantity.doubleValue();
        int quantity = doubleQuantity.intValue();
        if (quantity - doubleQuantity != 0.0)
        {
            return "Number should be integer";
        }

        if (quantity <= 0)
        {
            return "Number should be greater than zero";
        }

        return null;
    }

    public static Number parseQuantity(HttpServletRequest request, String quantityString)
    {
        try {
            Locale locale = request.getLocale();
            NumberFormat format = NumberFormat.getInstance(locale);
            return format.parse(quantityString);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
}
