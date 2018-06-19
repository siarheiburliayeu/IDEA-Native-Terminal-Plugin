package com.sburlyaev.cmd.plugin.model;

import com.intellij.openapi.diagnostic.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Command {

    private static final Logger log = Logger.getInstance(Command.class);

    private final String[] commands;

    public Command(String... commands) {
        this.commands = commands;
    }

    public void execute() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        log.info(processBuilder.command().toString());

        Process process = processBuilder.start();

        String errorMessage = new BufferedReader(new InputStreamReader(process.getErrorStream()))
                .lines().collect(Collectors.joining("\n"));
        if (!errorMessage.isEmpty()) {
            log.error(errorMessage);
        }
    }

    public String[] getCommands() {
        return commands;
    }
}
