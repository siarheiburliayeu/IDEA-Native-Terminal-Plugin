package com.sburlyaev.cmd.plugin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.model.OperationSystem;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;

class CommandBuilderTest {

    private static final String CURRENT_DIR = System.getProperty("user.dir");

    @Test
    void testCreateCommandForWindowsDefault() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, null);

        List<String> expected = List.of("cmd", "/c", "start", "cmd", "/K", "cd", "/d", CURRENT_DIR);
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForWindowsPowerShell() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, "powershell");

        List<String> expected =
            List.of("cmd", "/c", "start", "powershell", "-NoExit", "-Command", "Set-Location", "'" + CURRENT_DIR + "'");
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForConEmu() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, "C:\\Program Files\\ConEmu\\ConEmu64.exe");

        List<String> expected = List.of("C:\\Program Files\\ConEmu\\ConEmu64.exe", "-Dir", CURRENT_DIR);
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForConEmuWithBash() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        Command result =
            CommandBuilder.createCommand(env, CURRENT_DIR, "C:\\Program Files\\ConEmu\\ConEmu64.exe -run {bash}");

        List<String> expected =
            List.of("C:\\Program Files\\ConEmu\\ConEmu64.exe", "-Dir", CURRENT_DIR, "-run", "{bash}");
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForGitBash() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, "C:\\Program Files\\Git\\git-bash.exe");

        List<String> expected = List.of("C:\\Program Files\\Git\\git-bash.exe", "--cd=" + CURRENT_DIR);
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForLinuxWithGnome() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.LINUX, "4.15.0-10-generic", "gnome");
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, null);

        List<String> expected = List.of("gnome-terminal", "--working-directory", CURRENT_DIR);
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForLinuxGuiNull() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.LINUX, "4.15.0-10-generic", null);
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, null);

        List<String> expected = List.of("konsole", "--new-tab", "--workdir", CURRENT_DIR);
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForLinuxDBusLaunch() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.LINUX, "4.15.0-10-generic", null);
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, "dbus-launch gnome-terminal");

        List<String> expected = List.of("dbus-launch gnome-terminal", "--working-directory", CURRENT_DIR);
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForMacOsDefault() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.5.2", "X");
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, null);

        List<String> expected = List.of("open", CURRENT_DIR, "-a", "Terminal");
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForMacOsITerm() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.5.2", "X");
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, "iTerm");

        List<String> expected = List.of("open", CURRENT_DIR, "-a", "iTerm");
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForMacOsAlacritty() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.5.2", "X");
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, "Alacritty");

        List<String> expected = List.of("open", "-n", "-a", "Alacritty", "--args", "--working-directory", CURRENT_DIR);
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForMacOsKitty() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.5.2", "X");
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, "kitty");

        List<String> expected = List.of("open", CURRENT_DIR, "-a", "kitty");
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCreateCommandForMacOsHyper() throws FileNotFoundException {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.5.2", "X");
        Command result = CommandBuilder.createCommand(env, CURRENT_DIR, "hyper");

        List<String> expected = List.of("open", CURRENT_DIR, "-a", "hyper");
        assertEquals(expected, result.getCommands());
    }

    @Test
    void testCheckProjectDirectory() throws FileNotFoundException {
        CommandBuilder.checkProjectDirectory(CURRENT_DIR);
    }

    @Test
    void testCheckProjectDirectoryNotExist() {
        assertThrows(FileNotFoundException.class,
            () -> CommandBuilder.checkProjectDirectory("/dummyDir"));
    }

    @Test
    void testCheckProjectDirectoryNotDirectory() {
        String file = CURRENT_DIR + "/src/com/sburlyaev/cmd/plugin/CommandBuilder.java";

        assertThrows(FileNotFoundException.class,
            () -> CommandBuilder.checkProjectDirectory(file));
    }

}
