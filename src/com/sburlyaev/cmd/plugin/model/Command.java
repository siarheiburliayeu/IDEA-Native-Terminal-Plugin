package com.sburlyaev.cmd.plugin.model;

import com.intellij.openapi.diagnostic.Logger;

import java.io.IOException;
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
        new ProcessBuilder(commands).start();
    }

    public List<String> getCommands() {
        return new ArrayList<>(commands);
    }
}
