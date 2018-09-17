package com.es.phoneshop.resources;

import java.util.ListResourceBundle;

public class MyResources extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                {"notNumberError", "Not a number"},
                {"notPositiveNumberError", "Quantity must be >0"},
                {"success", "Products were successfully added."}
        };
    }
}
