package com.es.phoneshop.logic;

public class ApplicationService {
    private static ApplicationService object;

    private ApplicationService(){}

    public static ApplicationService getInstance() {
        if (object == null) {
            synchronized (ApplicationService.class) {
                if (object == null) {
                    object = new ApplicationService();
                }
            }

        }
        return object;
    }

    public String getProductId(String string){
        String[] words = string.split("/");
        return words[3];
    }
}
