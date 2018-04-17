package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.externalSystem.service.execution.NotSupportedException;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.model.OperationSystem;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;

import static org.junit.Assert.assertEquals;

public class OpenCmdTest {

    private OpenCmd testObject;

    @Before
    public void setUp() {
        testObject = new OpenCmd();
    }

    @Test
    public void testCreateCommandForWindows() {
        Environment env = new Environment(OperationSystem.WINDOWS, "10.0", "windows");
        String projectBaseDir = "test";
        Command result = testObject.createCommand(env, projectBaseDir);

        String expected = MessageFormat.format("cmd /c \"start cmd /K \"cd /d {0}\"", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForLinuxWithGnome() {
        Environment env = new Environment(OperationSystem.LINUX, "3.14", "gnome");
        String projectBaseDir = "test";
        Command result = testObject.createCommand(env, projectBaseDir);

        String expected = MessageFormat.format("gnome-terminal --working-directory={0}", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }

    @Test(expected = NotSupportedException.class)
    public void testCreateCommandForLinuxNonGnome() {
        Environment env = new Environment(OperationSystem.LINUX, "3.14", "xxx");
        String projectBaseDir = "test";
        testObject.createCommand(env, projectBaseDir);
    }

    @Test
    public void testCreateCommandForMacOS() {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.3", "X");
        String projectBaseDir = "test";
        Command result = testObject.createCommand(env, projectBaseDir);

        String expected = MessageFormat.format("open -a Terminal.app --new --fresh --args cd {0}", projectBaseDir);
        assertEquals(expected, result.getCommand());
    }
}
