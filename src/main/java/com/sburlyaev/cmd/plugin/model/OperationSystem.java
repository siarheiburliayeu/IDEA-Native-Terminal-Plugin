package com.sburlyaev.cmd.plugin.model;

import com.intellij.openapi.diagnostic.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum OperationSystem {
    WINDOWS("win"),
    LINUX("lin"),
    MAC_OS("mac");

    private static final Pattern OS_VERSION_PATTERN = Pattern.compile("^(\\d+\\.\\d+).*");

    private final String name;

    private static final Logger log = Logger.getInstance(OperationSystem.class);

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
            throw new RuntimeException("This Operation System is not supported: " + osName + " (" + os + ")");
        }
    }

    public static double parseOsVersion(String windowsVersion) {
        double version = -1.0;
        try {
            Matcher matcher = OS_VERSION_PATTERN.matcher(windowsVersion);
            if (matcher.matches()) {
                String group1 = matcher.group(1);
                version = Double.parseDouble(group1);
            }
        } catch (Exception e) {
            log.error("Failed to parse OS version: " + windowsVersion, e);
        }
        return version;
    }
}
