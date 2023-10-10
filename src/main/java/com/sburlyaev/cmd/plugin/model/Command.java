package com.sburlyaev.cmd.plugin.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Command {

    private final List<String> commands;

    public Command(String... commands) {
        this.commands = Arrays.stream(commands)
            .filter(Objects::nonNull)
            .collect(Collectors.toList()); // ArrayList
    }

    public void add(String... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public void execute(String path) throws IOException {
        new ProcessBuilder(commands)
            .directory(new File(path))
            .start();
    }

    public List<String> getCommands() {
        return List.copyOf(commands);
    }

    @Override
    public String toString() {
        return "Command{" +
            "commands=" + commands +
            '}';
    }

}
