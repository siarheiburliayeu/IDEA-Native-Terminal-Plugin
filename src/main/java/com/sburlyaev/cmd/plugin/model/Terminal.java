package com.sburlyaev.cmd.plugin.model;

import java.io.File;

public enum Terminal {
    // Windows
    CMDER("cmder"),
    COMMAND_PROMPT("cmd"),
    POWER_SHELL("powershell"),
    CON_EMU("conemu"),
    GIT_BASH("git-bash"),
    BASH("bash"),

    // Linux
    GNOME_TERMINAL("gnome-terminal", "/usr/bin/gnome-terminal"),
    KONSOLE("konsole", "/usr/bin/konsole"),
    RXVT("rxvt"),

    // macOS
    MAC_TERMINAL("Terminal"),
    I_TERM("iTerm"),

    GENERIC("");

    private final String command;
    private final String defaultPath;

    Terminal(String command) {
        this.command = command;
        this.defaultPath = null;
    }

    Terminal(String command, String defaultPath) {
        this.command = command;
        this.defaultPath = defaultPath;
    }

    public String getCommand() {
        return command;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public static Terminal fromString(String command) {
        String executable = getExecutable(command);
        if (containsIgnoreCase(executable, CMDER.command)) {
            return CMDER;
        } else if (containsIgnoreCase(executable, COMMAND_PROMPT.command)) {
            return COMMAND_PROMPT;
        } else if (containsIgnoreCase(executable, POWER_SHELL.command)) {
            return POWER_SHELL;
        } else if (containsIgnoreCase(executable, CON_EMU.command)) {
            return CON_EMU;
        } else if (containsIgnoreCase(executable, GIT_BASH.command)) {
            return GIT_BASH;
        } else if (containsIgnoreCase(executable, BASH.command)) {
            return BASH;
        } else if (containsIgnoreCase(executable, GNOME_TERMINAL.command)) {
            return GNOME_TERMINAL;
        } else if (containsIgnoreCase(executable, KONSOLE.command)) {
            return KONSOLE;
        } else if (containsIgnoreCase(executable, RXVT.command)) {
            return RXVT;
        } else if (containsIgnoreCase(executable, MAC_TERMINAL.command)) {
            return MAC_TERMINAL;
        } else if (containsIgnoreCase(executable, I_TERM.command)) {
            return I_TERM;
        } else {
            return GENERIC;
        }
    }

    protected static String getExecutable(String command) {
        File file = new File(command);
        return file.getName();
    }

    private static boolean containsIgnoreCase(String s1, String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase());
    }
}
