package com.es.phoneshop;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class PhoneshopMain {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        String path = "";

        for(int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "--war" -> {
                    ++i;
                    if (i >= args.length) {
                        throw new IllegalArgumentException("war value is not specified");
                    }
                    File war = new File(args[i]);
                    tomcat.addWebapp(path, war.getAbsolutePath());
                }
                case "--path" -> {
                    ++i;
                    if (i >= args.length) {
                        throw new IllegalArgumentException("path value is not specified");
                    }
                    path = args[i];
                }
                case "--port" -> {
                    ++i;
                    if (i >= args.length) {
                        throw new IllegalArgumentException("port value is not specified");
                    }
                    tomcat.setPort(Integer.parseInt(args[i]));
                }
            }
        }

        tomcat.getHost().setAppBase(".");

        tomcat.enableNaming();
        tomcat.getConnector();


        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        tomcat.getServer().await();
    }
}
