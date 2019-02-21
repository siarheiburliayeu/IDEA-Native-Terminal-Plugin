package com.sburlyaev.cmd.plugin.model;

public class Environment {

    private final OperationSystem os;
    private final String osVersion;
    private final String gui;

    public Environment(OperationSystem os, String osVersion, String gui) {
        this.os = os;
        this.osVersion = osVersion;
        this.gui = gui;
    }

    public static Environment getEnvironment() {
        // todo: use com.intellij.openapi.util.SystemInfo
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String gui = System.getProperty("sun.desktop");

        OperationSystem os = OperationSystem.fromString(osName);

        return new Environment(os, osVersion, gui);
    }

    public OperationSystem getOs() {
        return os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getGui() {
        return gui;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "os=" + os +
                ", osVersion='" + osVersion + '\'' +
                ", gui='" + gui + '\'' +
                '}';
    }
}
