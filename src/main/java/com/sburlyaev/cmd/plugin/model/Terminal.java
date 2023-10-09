package com.sburlyaev.cmd.plugin.model;

import java.io.File;

public enum Terminal {
    // Windows
    CMDER("cmder"),
    WINDOWS_TERMINAL("wt"),
    COMMAND_PROMPT("cmd"),
    POWER_SHELL("powershell"),
    CON_EMU("conemu"),
    GIT_BASH("git-bash"),
    BASH("bash"),
    WSL("wsl"),

    // Linux
    GNOME_TERMINAL("gnome-terminal", "/usr/bin/gnome-terminal"),
    KONSOLE("konsole", "/usr/bin/konsole"),
    RXVT("rxvt"),
    TERMINATOR("terminator"),
    KITTY("kitty"),

    // macOS
    MAC_TERMINAL("Terminal"),
    I_TERM("iTerm"),
    ALACRITTY("Alacritty"),

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
        return matchTerminal(getExecutable(command));
    }

    protected static Terminal matchTerminal(String fileName) {
        for (Terminal terminal : Terminal.values()) {
            if (terminal != GENERIC  // skip GENERIC because `contains("")` returns `true` for any string
                    && containsIgnoreCase(fileName, terminal.command)) {
                return terminal;
            }
        }
        return GENERIC;
    }

    protected static String getExecutable(String command) {
        return new File(command).getName();
    }

    private static boolean containsIgnoreCase(String s1, String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase());
    }
}
