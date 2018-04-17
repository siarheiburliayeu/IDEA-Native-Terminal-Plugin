package com.sburlyaev.cmd.plugin;

public class Test {

    public static void main(String[] args) {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String gui = System.getProperty("sun.desktop");

        String shortName = osName.substring(0, 3).toLowerCase();
        System.out.println("OS name: " + osName + " (" + shortName + ")");
        System.out.println("OS version : " + osVersion);
        System.out.println("GUI: " + gui);
    }
}
