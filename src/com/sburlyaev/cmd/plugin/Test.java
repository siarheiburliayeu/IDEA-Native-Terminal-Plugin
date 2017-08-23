package com.sburlyaev.cmd.plugin;

public class Test {

    public static void main(String[] args) {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String gui = System.getProperty("sun.desktop");

        System.out.println(osName + " " + osVersion);
        System.out.println(osName.toLowerCase().substring(0,3));
        System.out.println(gui);

    }
}
