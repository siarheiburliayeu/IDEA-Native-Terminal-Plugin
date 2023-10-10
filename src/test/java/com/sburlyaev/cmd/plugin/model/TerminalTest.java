package com.sburlyaev.cmd.plugin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TerminalTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "cmd",
        "cmd.exe",
        "CMD",
    })
    void testFromStringCmd(String command) {
        Terminal result = Terminal.fromString(command);
        assertEquals(Terminal.COMMAND_PROMPT, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "powershell",
        "powershell.exe",
        "PowerShell",
    })
    void testFromStringPowerShell(String command) {
        Terminal result = Terminal.fromString(command);
        assertEquals(Terminal.POWER_SHELL, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "conemu",
        "ConEmu64.exe",
        "C:/Program Files/ConEmu/ConEmu64.exe",
        "C:\\Program Files\\ConEmu\\ConEmu64.exe",
    })
    void testFromStringConEmu(String command) {
        Terminal result = Terminal.fromString(command);
        assertEquals(Terminal.CON_EMU, result);
    }

    @Test
    void testFromStringConEmuWithCmdInPath() {
        String command = String.join(File.separator, "C:", "cmds", "ConEmu", "ConEmu64.exe");
        Terminal result = Terminal.fromString(String.join(File.separator, command));
        assertEquals(Terminal.CON_EMU, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "git-bash",
        "git-bash.exe",
        "C:/Program Files/Git/git-bash.exe",
        "C:\\Program Files\\Git\\git-bash.exe",
    })
    void testFromStringGitBash(String command) {
        Terminal result = Terminal.fromString(command);
        assertEquals(Terminal.GIT_BASH, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "gnome-terminal",
        "/usr/bin/gnome-terminal",
        "dbus-launch gnome-terminal",
    })
    void testFromStringGnomeTerminal(String command) {
        Terminal result = Terminal.fromString(command);
        assertEquals(Terminal.GNOME_TERMINAL, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Terminal",
        "Terminal.app",
    })
    void testFromStringMacTerminal(String command) {
        Terminal result = Terminal.fromString(command);
        assertEquals(Terminal.MAC_TERMINAL, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "iterm",
        "iTerm",
        "iTerm.app",
    })
    void testFromStringITerm(String command) {
        Terminal result = Terminal.fromString(command);
        assertEquals(Terminal.I_TERM, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "alacritty",
        "Alacritty",
        "Alacritty.app",
    })
    void testFromStringAlacritty(String command) {
        Terminal result = Terminal.fromString(command);
        assertEquals(Terminal.ALACRITTY, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "kitty",
        "kitty.sh",
    })
    void testFromStringKitty(String command) {
        Terminal result = Terminal.fromString(command);
        assertEquals(Terminal.KITTY, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "hyper",
        "Hyper",
        "Hyper.app",
    })
    void testFromStringHyper(String command) {
        Terminal result = Terminal.fromString(command);
        assertEquals(Terminal.HYPER, result);
    }

}
