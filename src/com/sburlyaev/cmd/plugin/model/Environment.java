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
