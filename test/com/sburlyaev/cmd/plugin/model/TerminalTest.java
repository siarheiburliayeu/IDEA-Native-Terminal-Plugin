package com.sburlyaev.cmd.plugin.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TerminalTest {

    @Test
    public void testFromStringCmd() {
        Terminal result = Terminal.fromString("cmd");
        assertEquals(Terminal.COMMAND_PROMPT, result);
    }

    @Test
    public void testFromStringCmdExe() {
        Terminal result = Terminal.fromString("cmd.exe");
        assertEquals(Terminal.COMMAND_PROMPT, result);
    }

    @Test
    public void testFromStringCmdUpperCase() {
        Terminal result = Terminal.fromString("CMD");
        assertEquals(Terminal.COMMAND_PROMPT, result);
    }

    @Test
    public void testFromStringPowerShellLowerCase() {
        Terminal result = Terminal.fromString("powershell");
        assertEquals(Terminal.POWER_SHELL, result);
    }

    @Test
    public void testFromStringPowerShellExe() {
        Terminal result = Terminal.fromString("powershell.exe");
        assertEquals(Terminal.POWER_SHELL, result);
    }

    @Test
    public void testFromStringPowerShell() {
        Terminal result = Terminal.fromString("PowerShell");
        assertEquals(Terminal.POWER_SHELL, result);
    }

    @Test
    public void testFromStringGnomeTerminal() {
        Terminal result = Terminal.fromString("gnome-terminal");
        assertEquals(Terminal.GNOME_TERMINAL, result);
    }

    @Test
    public void testFromStringDBusLaunchGnomeTerminal() {
        Terminal result = Terminal.fromString("dbus-launch gnome-terminal");
        assertEquals(Terminal.GNOME_TERMINAL, result);
    }

    @Test
    public void testFromStringMacTerminal() {
        Terminal result = Terminal.fromString("Terminal");
        assertEquals(Terminal.MAC_TERMINAL, result);
    }

    @Test
    public void testFromStringMacTerminalApp() {
        Terminal result = Terminal.fromString("Terminal.app");
        assertEquals(Terminal.MAC_TERMINAL, result);
    }

    @Test
    public void testFromStringITerm() {
        Terminal result = Terminal.fromString("iTerm");
        assertEquals(Terminal.I_TERM, result);
    }
}
