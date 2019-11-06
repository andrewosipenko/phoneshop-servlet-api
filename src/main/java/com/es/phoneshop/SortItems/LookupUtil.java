package com.es.phoneshop.SortItems;

public class LookupUtil {
    public static <E extends Enum<E>> E lookup(Class<E> e, String id) {
        E result;
        try {
            result = Enum.valueOf(e, id);
        } catch (Exception ex) {
            result = null;
        }

        return result;
    }
}
