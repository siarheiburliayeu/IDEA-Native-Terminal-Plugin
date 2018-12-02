package com.sburlyaev.cmd.plugin.model;

import java.io.File;

public enum Terminal {
    COMMAND_PROMPT("cmd"),
    POWER_SHELL("powershell"),
    CON_EMU("conemu"),
    GIT_BASH("git-bash"),
    BASH("bash"),
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
        String executable = getExecutable(command);
        if (containsIgnoreCase(executable, COMMAND_PROMPT.command)) {
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
