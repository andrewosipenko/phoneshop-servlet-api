package com.es.phoneshop.web;

import java.util.Map;

import static com.es.phoneshop.web.constants.ControllerConstants.*;

//can be refactored to nonstatic class with possibility to add pages ulrs dynamically, for example to maintain
// adding to cart from customers private page (?)
public abstract class PageUrlHelper {

    private static Map<String, String> pages = Map.of(
            PRODUCT_LIST_PAGE_SHORT, PRODUCT_LIST_PAGE_PATH,
            PRODUCT_DETAILS_PAGE_SHORT, PRODUCT_DETAILS_PAGE_PATH,
            CART_PAGE_SHORT, CART_PAGE_PATH
    );

    public static String getPageUrl(String pageShortName, String productId) {
        return pageShortName.equals(PRODUCT_DETAILS_PAGE_SHORT)
                ? PRODUCT_DETAILS_PAGE_PATH + productId
                : pages.getOrDefault(pageShortName, PRODUCT_LIST_JSP_PATH);
    }
}
