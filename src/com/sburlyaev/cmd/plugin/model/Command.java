package com.sburlyaev.cmd.plugin.model;

import com.intellij.openapi.diagnostic.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command {

    private static final Logger log = Logger.getInstance(Command.class);

    private final List<String> commands;

    public Command(String... commands) {
        this.commands = Arrays.stream(commands)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void add(String... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public void execute() throws IOException {
        Process process = new ProcessBuilder(commands).start();

        String errorMessage = new BufferedReader(new InputStreamReader(process.getErrorStream()))
                .lines().collect(Collectors.joining("\n"));
        if (!errorMessage.isEmpty()) {
            log.error(errorMessage);
        }
    }

    public List<String> getCommands() {
        return new ArrayList<>(commands);
    }
}
