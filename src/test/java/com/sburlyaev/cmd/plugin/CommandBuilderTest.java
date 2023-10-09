package com.sburlyaev.cmd.plugin;

import static org.junit.Assert.assertEquals;

import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.model.OperationSystem;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import org.junit.Ignore;
import org.junit.Test;

// todo: review
public class CommandBuilderTest {

    @Test
    @Ignore
    public void testCreateCommandForWindowsDefault() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "C:\\Users\\user\\IdeaProjects\\IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, null);

        String expected = MessageFormat.format("cmd /c \"start cmd /K \"cd /d {0}\"", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForWindowsPowerShell() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "C:\\Users\\user\\IdeaProjects\\IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "powershell");

        String expected =
            MessageFormat.format("cmd /c start powershell -NoExit -Command \"Set-Location ''{0}''\"", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForConEmu() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "C:\\Users\\user\\IdeaProjects\\IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "C:\\Program Files\\ConEmu\\ConEmu64.exe");

        String expected = MessageFormat.format("C:\\Program Files\\ConEmu\\ConEmu64.exe -Dir \"{0}\"", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForConEmuWithBash() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "C:\\Users\\user\\IdeaProjects\\IDEA-Native-Terminal-Plugin";
        Command result =
            CommandBuilder.createCommand(env, projectBaseDir, "C:\\Program Files\\ConEmu\\ConEmu64.exe -run {bash}");

        String expected = MessageFormat.format("C:\\Program Files\\ConEmu\\ConEmu64.exe -Dir \"{0}\" -run '{'bash'}'",
            projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForGitBash() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "C:\\Users\\user\\IdeaProjects\\IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "C:\\Program Files\\Git\\git-bash.exe");

        String expected = MessageFormat.format("C:\\Program Files\\Git\\git-bash.exe --cd=\"{0}\"", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForLinuxWithGnome() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.LINUX, "4.15.0-10-generic", "gnome");
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, null);

        String expected = MessageFormat.format("gnome-terminal --working-directory={0}", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForLinuxGuiNull() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.LINUX, "4.15.0-10-generic", null);
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, null);

        String expected = MessageFormat.format("gnome-terminal --working-directory={0}", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForLinuxDBusLaunch() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.LINUX, "4.15.0-10-generic", null);
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "dbus-launch gnome-terminal");

        String expected = MessageFormat.format("dbus-launch gnome-terminal --working-directory={0}", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForMacOsDefault() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.3", "X");
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, null);

        String expected = MessageFormat.format("open ''{0}'' -a Terminal", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForMacOsITerm() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.3", "X");
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "iTerm");

        String expected = MessageFormat.format("open ''{0}'' -a iTerm", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForMacOsAlacritty() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.3", "X");
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "Alacritty");

        String expected = MessageFormat.format("open -n -a Alacritty --args --working-directory {0}", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCreateCommandForMacOsKitty() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.3", "X");
        String projectBaseDir = "/user/home/IdeaProjects/IDEA-Native-Terminal-Plugin";
        Command result = CommandBuilder.createCommand(env, projectBaseDir, "kitty");

        String expected = MessageFormat.format("open ''{0}'' -a kitty", projectBaseDir);
        assertEquals(expected, result.getCommands());
    }

    @Test
    @Ignore
    public void testCheckProjectDirectory() throws FileNotFoundException {
        String dir = System.getProperty("user.dir");
        CommandBuilder.checkProjectDirectory(dir);
    }

    @Test(expected = FileNotFoundException.class)
    public void testCheckProjectDirectoryNotExist() throws FileNotFoundException {
        CommandBuilder.checkProjectDirectory("/dummyDir");
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void testCheckProjectDirectoryNotDirectory() throws FileNotFoundException {
        String file = System.getProperty("user.dir") + "/src/com/sburlyaev/cmd/plugin/CommandBuilder.java";
        CommandBuilder.checkProjectDirectory(file);
    }

}
