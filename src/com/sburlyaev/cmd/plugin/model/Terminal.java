package com.sburlyaev.cmd.plugin.model;

public enum Terminal {
    COMMAND_PROMPT("cmd"),
    POWER_SHELL("powershell"),
    CON_EMU("conemu"),
    GIT_BASH("git-bash"),
    GNOME_TERMINAL("gnome-terminal"),
    RXVT("rxvt"),
    MAC_TERMINAL("Terminal"),
    I_TERM("iTerm"),
    GENERIC("");

    private final String command;

    Terminal(String name) {
        this.command = name;
    }

    public String getCommand() {
        return command;
    }

    public static Terminal fromString(String command) {
        if (containsIgnoreCase(command, COMMAND_PROMPT.command)) {
            return COMMAND_PROMPT;
        } else if (containsIgnoreCase(command, POWER_SHELL.command)) {
            return POWER_SHELL;
        } else if (containsIgnoreCase(command, CON_EMU.command)) {
            return CON_EMU;
        } else if (containsIgnoreCase(command, GIT_BASH.command)) {
            return GIT_BASH;
        } else if (containsIgnoreCase(command, GNOME_TERMINAL.command)) {
            return GNOME_TERMINAL;
        } else if (containsIgnoreCase(command, RXVT.command)) {
            return RXVT;
        } else if (containsIgnoreCase(command, MAC_TERMINAL.command)) {
            return MAC_TERMINAL;
        } else if (containsIgnoreCase(command, I_TERM.command)) {
            return I_TERM;
        } else {
            return GENERIC;
        }
    }

    private static boolean containsIgnoreCase(String s1, String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase());
    }
}
