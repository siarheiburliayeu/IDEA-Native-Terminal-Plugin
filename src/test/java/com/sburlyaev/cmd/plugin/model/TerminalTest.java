package com.sburlyaev.cmd.plugin.model;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

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
    public void testFromStringConEmuPath() {
        Terminal result = Terminal.fromString("C:/Program Files/ConEmu/ConEmu64.exe");
        assertEquals(Terminal.CON_EMU, result);
    }

    @Test
    public void testFromStringConEmuPath2() {
        Terminal result = Terminal.fromString("C:\\Program Files\\ConEmu\\ConEmu64.exe");
        assertEquals(Terminal.CON_EMU, result);
    }

    @Test
    public void testFromStringConEmuPathWithCmd() {
        Terminal result = Terminal.fromString("C:/cmds/ConEmu/ConEmu64.exe");
        assertEquals(Terminal.CON_EMU, result);
    }

    @Test
    @Ignore("Actual: COMMAND_PROMPT") // fixme
    public void testFromStringConEmuPathWithCmd2() {
        Terminal result = Terminal.fromString("C:\\cmds\\ConEmu\\ConEmu64.exe");
        assertEquals(Terminal.CON_EMU, result);
    }

    @Test
    public void testFromStringConEmu() {
        Terminal result = Terminal.fromString("conemu");
        assertEquals(Terminal.CON_EMU, result);
    }

    @Test
    public void testFromStringConEmu64Exe() {
        Terminal result = Terminal.fromString("ConEmu64.exe");
        assertEquals(Terminal.CON_EMU, result);
    }

    @Test
    public void testFromStringGitBash() {
        Terminal result = Terminal.fromString("git-bash");
        assertEquals(Terminal.GIT_BASH, result);
    }

    @Test
    public void testFromStringGitBashExe() {
        Terminal result = Terminal.fromString("git-bash.exe");
        assertEquals(Terminal.GIT_BASH, result);
    }

    @Test
    public void testFromStringGitBashPath() {
        Terminal result = Terminal.fromString("C:/Program Files/Git/git-bash.exe");
        assertEquals(Terminal.GIT_BASH, result);
    }

    @Test
    public void testFromStringGitBashPath2() {
        Terminal result = Terminal.fromString("C:\\Program Files\\Git\\git-bash.exe");
        assertEquals(Terminal.GIT_BASH, result);
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
