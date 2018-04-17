package com.sburlyaev.cmd.plugin.model;

import com.intellij.openapi.externalSystem.service.execution.NotSupportedException;

public enum OperationSystem {
    WINDOWS("win"),
    LINUX("lin"),
    MAC_OS("mac");

    private final String name;

    OperationSystem(String name) {
        this.name = name;
    }

    public static OperationSystem fromString(String osName) {
        String os = osName.substring(0, 3).toLowerCase();

        if (WINDOWS.name.equals(os)) {
            return WINDOWS;
        } else if (LINUX.name.equals(os)) {
            return LINUX;
        } else if (MAC_OS.name.equals(os)) {
            return MAC_OS;
        } else {
            throw new NotSupportedException("This Operation System is not supported: " + osName + " (" + os + ")");
        }
    }
}
