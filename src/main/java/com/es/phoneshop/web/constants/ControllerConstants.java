package com.es.phoneshop.web.constants;

public abstract class ControllerConstants {

    public static final String PRODUCT_LIST_PAGE_SHORT = "PLP";
    public static final String PRODUCT_DETAILS_PAGE_SHORT = "PDP";
    public static final String CART_PAGE_SHORT = "CART";

    public static final String PRODUCT_LIST_PAGE_PATH = "/products";
    public static final String PRODUCT_DETAILS_PAGE_PATH = "/products/";
    public static final String CART_PAGE_PATH = "/cart";
    public static final String CHECKOUT_PAGE_PATH = "/checkout";
    public static final String ORDER_OVERVIEW_PAGE_PATH = "/order/overview/";

    public static final String PRODUCT_LIST_JSP_PATH = "/WEB-INF/pages/productList.jsp";
    public static final String PRICE_HISTORY_JSP_PATH = "/WEB-INF/pages/priceHistoryPage.jsp";
    public static final String PRODUCT_DETAILS_JSP_PATH = "/WEB-INF/pages/productDetails.jsp";
    public static final String MINI_CART_JSP_PATH = "/WEB-INF/pages/miniCart.jsp";
    public static final String CART_JSP_PATH = "/WEB-INF/pages/cart.jsp";
    public static final String CHECKOUT_JSP_PATH = "/WEB-INF/pages/checkout.jsp";
    public static final String ORDER_OVERVIEW_JSP_PATH = "/WEB-INF/pages/orderOverview.jsp";

    public static final String NOT_A_NUMBER_ERROR_MESSAGE = "Not a number";
    public static final String OUT_OF_STOCK_ERROR_MESSAGE = "Not enough stock";
    public static final String ADDING_TO_CART_SUCCESS_MESSAGE = "Added to cart successfully";
    public static final String REQUIRED_VALUE_ERROR_MESSAGE = "Value is required";
    public static final String WRONG_DATE_INPUT_ERROR_MESSAGE = "Incorrect date input, please follow YYYY-MM-DD format";
    public static final String PAST_DATE_ERROR_MESSAGE = "This date is no longer relevant, check your calendar :)";
    public static final String WRONG_PHONE_FORMAT_ERROR_MESSAGE = "Wrong phone number format, e.g. +375(17)7777777";
}
