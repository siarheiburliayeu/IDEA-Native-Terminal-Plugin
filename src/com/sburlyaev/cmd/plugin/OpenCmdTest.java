package com.sburlyaev.cmd.plugin;

import com.intellij.openapi.externalSystem.service.execution.NotSupportedException;
import com.sburlyaev.cmd.plugin.model.Command;
import com.sburlyaev.cmd.plugin.model.Environment;
import com.sburlyaev.cmd.plugin.model.OperationSystem;
import org.junit.Before;
import org.junit.Test;

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
        Command result = testObject.createCommand(env, "result");

        String expected = "cmd /c \"start cmd /K \"cd /d result\"";
        assertEquals(expected, result.getCommand());
    }

    @Test
    public void testCreateCommandForLinuxWithGnome() {
        Environment env = new Environment(OperationSystem.LINUX, "3.14", "gnome");
        Command result = testObject.createCommand(env, "result");

        String expected = "gnome-terminal --working-directory=result";
        assertEquals(expected, result.getCommand());
    }

    @Test(expected = NotSupportedException.class)
    public void testCreateCommandForLinuxNonGnome() {
        Environment env = new Environment(OperationSystem.LINUX, "3.14", "xxx");
        testObject.createCommand(env, "result");
    }

    @Test
    public void testCreateCommandForMacOS() {
        Environment env = new Environment(OperationSystem.MAC_OS, "13.3", "X");
        Command result = testObject.createCommand(env, "result");

        String expected = "open -a Terminal.app --new --fresh --args cd result";
        assertEquals(expected, result.getCommand());
    }
}
