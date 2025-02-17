package com.sburlyaev.cmd.plugin.model;

import java.io.File;

public enum Terminal {
    // Windows
    BASH("bash"),
    CMDER("cmder"),
    COMMAND_PROMPT("cmd"),
    CON_EMU("conemu"),
    GIT_BASH("git-bash"),
    POWER_SHELL("powershell"),
    POWER_SHELL_7("pwsh"),
    WINDOWS_TERMINAL("wt"),
    WSL("wsl"),

    // Linux
    GHOSTTY_LINUX("ghostty", "/usr/bin/ghostty"),
    GNOME_TERMINAL("gnome-terminal", "/usr/bin/gnome-terminal"),
    KITTY("kitty"),
    KONSOLE("konsole", "/usr/bin/konsole"),
    RXVT("rxvt"),
    TERMINATOR("terminator"),

    // macOS
    ALACRITTY("Alacritty"),
    GHOSTTY_MAC("Ghostty", "/usr/local/bin/Ghostty"),
    HYPER("Hyper"),
    I_TERM("iTerm"),
    MAC_TERMINAL("Terminal"),

    // Generic
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

    private static Terminal matchTerminal(String fileName) {
        for (Terminal terminal : Terminal.values()) {
            if (terminal != GENERIC  // skip GENERIC because `contains("")` returns `true` for any string
                    && containsIgnoreCase(fileName, terminal.command)) {
                return terminal;
            }
        }
        return GENERIC;
    }

    private static String getExecutable(String command) {
        return new File(command).getName();
    }

    private static boolean containsIgnoreCase(String s1, String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase());
    }

}
