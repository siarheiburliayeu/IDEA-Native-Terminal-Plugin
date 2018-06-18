package com.sburlyaev.cmd.plugin;

import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.model.OperationSystem;
import org.junit.Test;

import java.text.MessageFormat;

import static org.junit.Assert.assertEquals;

public class CommandBuilderTest {

    @Test
    public void testCreateCommandForWindowsDefault() {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "C:\\Users\\user\\IdeaProjects\\IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, null);

        String expected = MessageFormat.format("cmd /c \"start cmd /K \"cd /d {0}\"", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForWindowsPowerShell() {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "C:\\Users\\user\\IdeaProjects\\IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "powershell");

        String expected = MessageFormat.format("cmd /c start powershell -NoExit -Command \"Set-Location ''{0}''\"", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForConEmu() {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "C:\\Users\\user\\IdeaProjects\\IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "C:\\Program Files\\ConEmu\\ConEmu64.exe");

        String expected = MessageFormat.format("C:\\Program Files\\ConEmu\\ConEmu64.exe -Dir \"{0}\"", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForConEmuWithBash() {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "C:\\Users\\user\\IdeaProjects\\IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "C:\\Program Files\\ConEmu\\ConEmu64.exe -run {bash}");

        String expected = MessageFormat.format("C:\\Program Files\\ConEmu\\ConEmu64.exe -Dir \"{0}\" -run '{'bash'}'", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForGitBash() {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "C:\\Users\\user\\IdeaProjects\\IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "C:\\Program Files\\Git\\git-bash.exe");

        String expected = MessageFormat.format("C:\\Program Files\\Git\\git-bash.exe --cd=\"{0}\"", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForLinuxWithGnome() {
        Environment env = new Environment(OperationSystem.LINUX, "4.15.0-10-generic", "gnome");
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, null);

        String expected = MessageFormat.format("gnome-terminal --working-directory={0}", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForLinuxGuiNull() {
        Environment env = new Environment(OperationSystem.LINUX, "4.15.0-10-generic", null);
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, null);

        String expected = MessageFormat.format("gnome-terminal --working-directory={0}", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForLinuxDBusLaunch() {
        Environment env = new Environment(OperationSystem.LINUX, "4.15.0-10-generic", null);
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "dbus-launch gnome-terminal");

        String expected = MessageFormat.format("dbus-launch gnome-terminal --working-directory={0}", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForMacOsDefault() {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.3", "X");
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, null);

        String expected = MessageFormat.format("open ''{0}'' -a Terminal", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForMacOsITerm() {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.3", "X");
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "iTerm");

        String expected = MessageFormat.format("open ''{0}'' -a iTerm", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }
}
