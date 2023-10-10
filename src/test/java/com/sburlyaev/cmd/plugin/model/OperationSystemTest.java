package com.sburlyaev.cmd.plugin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class OperationSystemTest {

    @ParameterizedTest
    @ValueSource(strings = {"Windows", "Windows 10", "Windows 11.0"})
    void testFromStringWindows(String osName) {
        OperationSystem result = OperationSystem.fromString(osName);
        assertEquals(OperationSystem.WINDOWS, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Linux", "Linux 6.5.6-42-generic"})
    void testFromStringLinux(String osName) {
        OperationSystem result = OperationSystem.fromString(osName);
        assertEquals(OperationSystem.LINUX, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MacOS", "Mac OS X", "Mac OS X 10.15.7"})
    void testFromStringMacOS(String osName) {
        OperationSystem result = OperationSystem.fromString(osName);
        assertEquals(OperationSystem.MAC_OS, result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"NotSupportedOS"})
    void testFromStringNotSupported(String osName) {
        assertThrows(RuntimeException.class,
            () -> OperationSystem.fromString(osName));
    }

}
