package com.sburlyaev.cmd.plugin.model;

import static com.sburlyaev.cmd.plugin.model.Terminal.COMMAND_PROMPT;
import static com.sburlyaev.cmd.plugin.model.Terminal.GNOME_TERMINAL;
import static com.sburlyaev.cmd.plugin.model.Terminal.MAC_TERMINAL;

import com.intellij.openapi.diagnostic.Logger;

public enum OperationSystem {
    WINDOWS("win", COMMAND_PROMPT),
    LINUX("lin", GNOME_TERMINAL),
    MAC_OS("mac", MAC_TERMINAL);

    private final String name;
    private final Terminal defaultTerminal;

    private static final Logger log = Logger.getInstance(OperationSystem.class);

    OperationSystem(String name, Terminal defaultTerminal) {
        this.name = name;
        this.defaultTerminal = defaultTerminal;
    }

    public Terminal getDefaultTerminal() {
        return defaultTerminal;
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

    public static double parseWindowsVersion(String windowsVersion) {
        double version = -1.0;
        try {
            version = Double.parseDouble(windowsVersion);
        } catch (Exception e) {
            log.error("Failed to parse Windows version: " + windowsVersion, e);
        }
        return version;
    }
}
