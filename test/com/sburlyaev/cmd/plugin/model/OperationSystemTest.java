package com.sburlyaev.cmd.plugin.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OperationSystemTest {

    @Test
    public void testFromStringWindows() {
        OperationSystem result = OperationSystem.fromString("Windows 10");
        assertEquals(OperationSystem.WINDOWS, result);
    }

    @Test
    public void testFromStringLinux() {
        OperationSystem result = OperationSystem.fromString("Linux");
        assertEquals(OperationSystem.LINUX, result);
    }

    @Test
    public void testFromStringMacOS() {
        OperationSystem result = OperationSystem.fromString("MacOS");
        assertEquals(OperationSystem.MAC_OS, result);
    }

    @Test(expected = RuntimeException.class)
    public void testFromStringNotSupported() {
        OperationSystem.fromString("NotSupportedOS");
    }
}
